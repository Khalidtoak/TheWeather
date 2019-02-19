package khalid.com.forecastAppmvvm2.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import khalid.com.forecastAppmvvm2.data.db.entity.FutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.details.ImperialDetailFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.details.MetricDetailFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.ImperialSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 12/30/2018.
 */
@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastsMetric(startDate: LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastsImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)
    @Query("select * from future_weather where date(date) = date(:date)")

    fun getDetailedWeatherByDateMetric(date: LocalDate): LiveData<MetricDetailFutureWeatherEntry>

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getDetailedWeatherByDateImperial(date: LocalDate): LiveData<ImperialDetailFutureWeatherEntry>

}