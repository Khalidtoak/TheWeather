package khalid.com.forecastAppmvvm2.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import khalid.com.forecastAppmvvm2.data.db.entity.CURRENT_LOCATION_ID
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation

/**
 * Created by ${KhalidToak} on 12/20/2018.
 */
@Dao
interface WeatherLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $CURRENT_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
    @Query("select * from weather_location where id = $CURRENT_LOCATION_ID")
    fun getLocationNotLive(): WeatherLocation?

}