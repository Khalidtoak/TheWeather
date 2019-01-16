package khalid.com.forecastAppmvvm2.ui.weather.current

import androidx.lifecycle.ViewModel;
import khalid.com.forecastAppmvvm2.internal.UnitSystem
import khalid.com.forecastAppmvvm2.internal.lazyDeferred
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.providers.UnitProviderImpl
import khalid.com.forecastAppmvvm2.repository.ForecastRepository
import khalid.com.forecastAppmvvm2.ui.base.WeatherViewModel

class CurrentWeatherViewModel(private val repository: ForecastRepository,
                              unitProvider: UnitProvider
) : WeatherViewModel(repository, unitProvider) {
    val weather by lazyDeferred {
        repository.getCurrentWeather(super.isMetricUnit)
    }

}
