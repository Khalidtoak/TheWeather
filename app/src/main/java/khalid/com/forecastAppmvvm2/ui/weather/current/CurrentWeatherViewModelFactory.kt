package khalid.com.forecastAppmvvm2.ui.weather.current
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import khalid.com.forecastAppmvvm2.providers.UnitProvider
import khalid.com.forecastAppmvvm2.providers.UnitProviderImpl
import khalid.com.forecastAppmvvm2.repository.ForecastRepository
class CurrentWeatherViewModelFactory(
    private val repository: ForecastRepository
,   private val unitProvider: UnitProvider
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(repository,unitProvider) as T
    }

}