package khalid.com.forecastAppmvvm2.repository

import androidx.lifecycle.LiveData
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.current.UnitSpecificWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.details.UnitSpecificDetailFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 11/20/2018.
 */
interface ForecastRepository{
    suspend fun getCurrentWeather(metric:Boolean) : LiveData<out UnitSpecificWeatherEntry>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean):
            LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getWeatherLcation() :LiveData<WeatherLocation>
    suspend fun getFutureWeatherByDate(date : LocalDate, metric: Boolean) : LiveData<out UnitSpecificDetailFutureWeatherEntry>
}