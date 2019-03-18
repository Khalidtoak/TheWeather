package khalid.com.forecastAppmvvm2

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import khalid.com.forecastAppmvvm2.data.db.ForecastDatabase
import khalid.com.forecastAppmvvm2.data.response.network.ConnectivityInterceptor
import khalid.com.forecastAppmvvm2.data.response.network.ConnectivityInterceptorImpl
import khalid.com.forecastAppmvvm2.data.response.network.WeatherApiService
import khalid.com.forecastAppmvvm2.data.response.network.WeatherNetworkDataSource
import khalid.com.forecastAppmvvm2.data.response.network.WeatherNetworkDataSourceImpl
import khalid.com.forecastAppmvvm2.providers.LocaionProviderImpl
import khalid.com.forecastAppmvvm2.providers.LocationProvider
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.providers.UnitProviderImpl
import khalid.com.forecastAppmvvm2.repository.ForecastRepository
import khalid.com.forecastAppmvvm2.repository.ForecastRepositoryImpl
import khalid.com.forecastAppmvvm2.ui.weather.current.CurrentWeatherViewModelFactory
import khalid.com.forecastAppmvvm2.ui.weather.future.detail.FutureWeatherDetailViewModelFactory
import khalid.com.forecastAppmvvm2.ui.weather.future.list.FutureWeatherListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 11/20/2018.
 */
class ForecastApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))
        //...
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocaionProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureWeatherListViewModelFactory(instance(), instance()) }
        bind() from factory { detailDate : LocalDate ->
            FutureWeatherDetailViewModelFactory(detailDate , instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
       // PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}