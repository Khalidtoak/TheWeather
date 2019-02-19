package khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list

import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 12/30/2018.
 */
interface UnitSpecificSimpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}