package khalid.com.forecastAppmvvm2.data.response.network

import androidx.lifecycle.LiveData
import khalid.com.forecastAppmvvm2.data.response.CurrentWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.FutureWeatherResponse

/**
 * Created by ${KhalidToak} on 11/20/2018.
 */
interface WeatherNetworkDataSource {
    val downloadedCurrentWeather  : LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>
    suspend fun getCurrentWeather(location: String, languageCode: String)
    suspend fun getFutureWeather(location: String,  languageCode: String)
}