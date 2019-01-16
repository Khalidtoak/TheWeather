package khalid.com.forecastAppmvvm2.providers

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation
import khalid.com.forecastAppmvvm2.internal.LocationNotPermitedException
import khalid.com.forecastAppmvvm2.internal.asDeferred
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val USE_PREFERRED_LOCATION = "CUSTOM_LOCATION"
class LocaionProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context) :PreferenceProvider(context), LocationProvider {
    private val appContext = context.applicationContext
    override suspend fun hasLocaionChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        }
        catch (e: LocationNotPermitedException){
           false
        }
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        if(!isUsingDeviceLocation()) {
            val customLocationName = getWeatherLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun getWeatherLocationName(): String? {
        return preferences.getString(USE_PREFERRED_LOCATION, null)
    }

    override suspend fun getPreferredLocationString() : String {
       if (isUsingDeviceLocation()) {
           try {
               val deviceLocation= getLastDeviceLocation().await()
               ?: return "${getWeatherLocationName()}"
               return "${deviceLocation.longitude} , ${deviceLocation.longitude}"
           }catch (e: LocationNotPermitedException){
               return "${getWeatherLocationName()}"
           }
       }
        else {
           return "${getWeatherLocationName()}"
       }

    }
    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean{
        if (!isUsingDeviceLocation()){
            return false
        }
        val deviceLocation = getLastDeviceLocation().await()
        ?: return false
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold

    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location> {
       return if (hasLocationPermission()) fusedLocationProviderClient.lastLocation.asDeferred()
        else  throw LocationNotPermitedException()

    }

    private fun isUsingDeviceLocation(): Boolean{
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }
    private fun hasLocationPermission() : Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}