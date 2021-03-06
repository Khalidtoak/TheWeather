package khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 12/30/2018.
 */
class ImperialSimpleFutureWeatherEntry (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempF")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificSimpleFutureWeatherEntry