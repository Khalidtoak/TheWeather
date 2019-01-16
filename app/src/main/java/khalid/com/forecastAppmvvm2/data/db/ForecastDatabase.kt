package khalid.com.forecastAppmvvm2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import khalid.com.forecastAppmvvm2.data.Current
import khalid.com.forecastAppmvvm2.data.db.entity.FutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation

/**
 * Created by ${KhalidToak} on 11/12/2018.
 */
@Database(
    entities = [Current::class, FutureWeatherEntry::class, WeatherLocation::class], version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase(){
    abstract fun currentWeatherDao() : WeatherDao
    abstract fun futureWeatherDDao() : FutureWeatherDao
    abstract  fun weatherocationDao() : WeatherLocationDao
    companion object {
        @Volatile private var instance : ForecastDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context :Context) = instance?: synchronized(LOCK) {
            instance ?:  buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, ForecastDatabase::class.java,
                    "forecast.db").build()
    }
}