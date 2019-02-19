package khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.details

import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 2/6/2019.
 */
interface UnitSpecificDetailFutureWeatherEntry {
    val date: LocalDate
    val maxTemperature: Double
    val minTemperature: Double
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val maxWindSpeed: Double
    val totalPrecipitation: Double
    val avgVisibilityDistance: Double
    val uv: Double
}