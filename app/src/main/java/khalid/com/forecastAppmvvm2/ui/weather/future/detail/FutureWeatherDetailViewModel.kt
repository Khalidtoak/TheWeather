package khalid.com.forecastAppmvvm2.ui.weather.future.detail
import khalid.com.forecastAppmvvm2.internal.lazyDeferred
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.repository.ForecastRepository
import khalid.com.forecastAppmvvm2.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureWeatherDetailViewModel(private val detailDate: LocalDate,
                                   private val forecastRepository: ForecastRepository,
                                   unitProvider: UnitProvider
) :  WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}
