package khalid.com.forecastAppmvvm2.data.response.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import khalid.com.forecastAppmvvm2.data.response.CurrentWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.FutureWeatherResponse
import khalid.com.forecastAppmvvm2.internal.NoNetworkException
import khalid.com.forecastAppmvvm2.repository.BaseRepositry

const val FORCAST_DAYS_COUNT = 7
class WeatherNetworkDataSourceImpl(private val weatherApiService
                                     : WeatherApiService) : WeatherNetworkDataSource, BaseRepositry() {
    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun getFutureWeather(location: String,  languageCode: String) {
        try {
            val futureWeatherResponse = safeApiCall(
                call = {weatherApiService.getFutureWeatherAsync(location,
                FORCAST_DAYS_COUNT).await()},
                errorMessage = "Error fetching weather")
            _downloadedFutureWeather.postValue(futureWeatherResponse)
        }
        catch (e: NoNetworkException){
            Log.e("Connectivity_error", "No internet connection", e)
        }
    }

    private val _dowloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get()  =  _dowloadedCurrentWeather

    override suspend fun getCurrentWeather(location: String, languageCode: String) {
        try {
            val currentWeatherResponse = safeApiCall(
                call = {weatherApiService.getCurrentWeatherAsync(location).await()},
                errorMessage = "Error fetching weather")
            _dowloadedCurrentWeather.postValue(currentWeatherResponse)
        }
        catch (e : NoNetworkException){
            Log.e("Connectivity_error", "No internet connection", e)
        }

    }
}