package khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.details

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 2/6/2019.
 */
data class MetricDetailFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "maxtempC")
    override val maxTemperature: Double,
    @ColumnInfo(name = "mintempC")
    override val minTemperature: Double,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxwindKph")
    override val maxWindSpeed: Double,
    @ColumnInfo(name = "totalprecipMm")
    override val totalPrecipitation: Double,
    @ColumnInfo(name = "avgvisKm")
    override val avgVisibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double
) : UnitSpecificDetailFutureWeatherEntry