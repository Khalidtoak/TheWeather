package khalid.com.forecastAppmvvm2.data.db.unitLocalized.current

/**
 * Created by ${KhalidToak} on 11/12/2018.
 */
interface UnitSpecificWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}