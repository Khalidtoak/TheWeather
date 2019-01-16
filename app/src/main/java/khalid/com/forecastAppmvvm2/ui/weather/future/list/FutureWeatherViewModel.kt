package khalid.com.forecastAppmvvm2.ui.weather.future.list

import androidx.lifecycle.ViewModel;
import khalid.com.forecastAppmvvm2.internal.lazyDeferred
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.repository.ForecastRepository
import khalid.com.forecastAppmvvm2.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureWeatherViewModel(repository: ForecastRepository, unitProvider: UnitProvider)
    : WeatherViewModel(repository,unitProvider){
    val weatherEntries by lazyDeferred{
        repository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}
