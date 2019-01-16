
package khalid.com.forecastAppmvvm2.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import khalid.com.forecastAppmvvm2.data.CURRENT_WEATHER_ID
import khalid.com.forecastAppmvvm2.data.Current
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.current.ImperialCurrentWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.current.MetricCurrentWeatherEntry

/**
 * Created by ${KhalidToak} on 11/12/2018.
 */
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(current: Current)
    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>
    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>
}