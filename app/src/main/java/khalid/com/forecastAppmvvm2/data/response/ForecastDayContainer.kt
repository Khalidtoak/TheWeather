package khalid.com.forecastAppmvvm2.data.response

import com.google.gson.annotations.SerializedName
import khalid.com.forecastAppmvvm2.data.db.entity.FutureWeatherEntry

data class ForecastDayContainer(
    @SerializedName("entries")
    val entries: List<FutureWeatherEntry>
)