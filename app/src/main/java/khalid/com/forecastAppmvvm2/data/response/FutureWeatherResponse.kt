package khalid.com.forecastAppmvvm2.data.response

import com.google.gson.annotations.SerializedName
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDayContainer,
    val location: WeatherLocation
)