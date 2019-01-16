package khalid.com.forecastAppmvvm2.providers

import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation

/**
 * Created by ${KhalidToak} on 12/20/2018.
 */
interface LocationProvider {
    suspend fun hasLocaionChanged(lastWeatherLocation: WeatherLocation) : Boolean
    suspend fun getPreferredLocationString(): String
}