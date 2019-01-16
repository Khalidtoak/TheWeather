package khalid.com.forecastAppmvvm2.ui.base

/**
 * Created by ${KhalidToak} on 1/15/2019.
 */
import androidx.lifecycle.ViewModel
import khalid.com.forecastAppmvvm2.internal.UnitSystem
import khalid.com.forecastAppmvvm2.internal.lazyDeferred
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.repository.ForecastRepository


abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLcation()
    }
}