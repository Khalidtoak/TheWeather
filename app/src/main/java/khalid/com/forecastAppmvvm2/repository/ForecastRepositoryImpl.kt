package khalid.com.forecastAppmvvm2.repository

import androidx.lifecycle.LiveData
import khalid.com.forecastAppmvvm2.data.db.FutureWeatherDao
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.current.UnitSpecificWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.WeatherDao
import khalid.com.forecastAppmvvm2.data.db.WeatherLocationDao
import khalid.com.forecastAppmvvm2.data.db.entity.WeatherLocation
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.details.UnitSpecificDetailFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.response.CurrentWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.FutureWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.network.FORCAST_DAYS_COUNT
import khalid.com.forecastAppmvvm2.data.response.network.WeatherNetworkDataSource
import khalid.com.forecastAppmvvm2.providers.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: WeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val currentWeatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
    ) : ForecastRepository {
    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getSimpleWeatherForecastsMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastsImperial(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) futureWeatherDao.getDetailedWeatherByDateMetric(date)
            else futureWeatherDao.getDetailedWeatherByDateImperial(date)
        }
    }
    override suspend fun getWeatherLcation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(metric :Boolean): LiveData<out UnitSpecificWeatherEntry> {
        initWeatherData()
        return withContext(Dispatchers.IO){
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    init {
        currentWeatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever {newCurrentWeather ->
                persisFetchedCurrentWeather(newCurrentWeather)
        }
            downloadedFutureWeather.observeForever{
                newFutureWeather->
                persistFutureWeather(newFutureWeather)
            }

        }
    }

    private fun persistFutureWeather(newFutureWeather: FutureWeatherResponse) {
       fun deleteOldEnries(){
           val today = LocalDate.now()
           futureWeatherDao.deleteOldEntries(today)
       }
        GlobalScope.launch (Dispatchers.IO) {
            deleteOldEnries()
            val futureWeatherList = newFutureWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(newFutureWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocationNotLive()
        if (lastWeatherLocation == null || locationProvider.hasLocaionChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            if(isFetchFutureNeeded())
               fetchCurrentWeather()

    }

    private suspend fun fetchFutureWeather() {
        currentWeatherNetworkDataSource.getFutureWeather(locationProvider.getPreferredLocationString(),
            Locale.getDefault().language)
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val fetchWeatherCount = futureWeatherDao.countFutureWeather(today)
        return fetchWeatherCount< FORCAST_DAYS_COUNT
    }

    private fun isFetchCurrentNeeded(lastfetchedtime : ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return  lastfetchedtime.isBefore(thirtyMinutesAgo)
    }
    private suspend fun fetchCurrentWeather(){
        currentWeatherNetworkDataSource.getCurrentWeather(locationProvider.getPreferredLocationString(), Locale.getDefault().language)
    }
    private  fun persisFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

}