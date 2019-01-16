package khalid.com.forecastAppmvvm2.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.repository.ForecastRepository


/**
 * Created by ${KhalidToak} on 1/15/2019.
 */
class FutureWeatherListViewModelFactory(
private val repository: ForecastRepository
,   private val unitProvider: UnitProvider
) :
ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureWeatherViewModel(repository, unitProvider) as T
    }
}