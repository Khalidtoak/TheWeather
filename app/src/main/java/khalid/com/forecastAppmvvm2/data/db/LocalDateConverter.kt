package khalid.com.forecastAppmvvm2.data.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by ${KhalidToak} on 12/30/2018.
 */

object LocalDateConverter {
    @TypeConverter
    @JvmStatic
    fun stringToDate(string: String?) = string.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }
    @TypeConverter
    @JvmStatic
    fun dateToSring(dateTime: LocalDate?) = dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)
}