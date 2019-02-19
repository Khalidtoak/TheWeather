package khalid.com.forecastAppmvvm2.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.repository.ForecastRepository
import org.threeten.bp.LocalDate

/**
 * Created by ${KhalidToak} on 2/19/2019.
 */
class FutureWeatherDetailViewModelFactory (
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureWeatherDetailViewModel(detailDate, forecastRepository, unitProvider) as T
    }
}