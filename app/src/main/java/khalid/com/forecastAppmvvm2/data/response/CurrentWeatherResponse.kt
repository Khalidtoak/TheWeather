package khalid.com.forecastAppmvvm2.data.response

/**
 * Created by ${KhalidToak} on 12/30/2018.
 */
import com.google.gson.annotations.SerializedName
import khalid.com.forecastAppmvvm2.data.Current
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: Current
)