package khalid.com.forecastAppmvvm2.ui

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

/**
 * Created by ${KhalidToak} on 12/27/2018.
 */
    class LifeCycleAwareLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback) : LifecycleObserver{
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }
    private val locationRequest: LocationRequest = LocationRequest()
        .apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdate() = fusedLocationProviderClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        null)
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdate() = fusedLocationProviderClient.removeLocationUpdates(locationCallback)

}