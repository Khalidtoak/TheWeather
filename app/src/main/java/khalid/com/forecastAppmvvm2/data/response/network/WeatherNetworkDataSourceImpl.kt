package khalid.com.forecastAppmvvm2.data.response.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import khalid.com.forecastAppmvvm2.data.response.CurrentWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.FutureWeatherResponse
import khalid.com.forecastAppmvvm2.internal.NoNetworkException

const val FORCAST_DAYS_COUNT = 7
class WeatherNetworkDataSourceImpl(private val weatherApiService
                                     : WeatherApiService) : WeatherNetworkDataSource {
    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun getFutureWeather(location: String,  languageCode: String) {
        try {
            val fetchedFutureWeather = weatherApiService.getFutureWeather(location,
                FORCAST_DAYS_COUNT).await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
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
            val fetchedCurrentWeather = weatherApiService.getCurrentWeather(location)
                .await()
            _dowloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e : NoNetworkException){
            Log.e("Connectivity_error", "No internet connection", e)
        }

    }
}