package khalid.com.forecastAppmvvm2.ui.weather.current

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import khalid.com.forecastAppmvvm2.R
import khalid.com.forecastAppmvvm2.internal.glide.GlideApp
import khalid.com.forecastAppmvvm2.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : CurrentWeatherViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        bindUI()

    }

    private fun bindUI() =
    launch {
        val location = viewModel.weatherLocation.await()
        location.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer
            updateLocation(it.name)
        })
        val weather = viewModel.weather.await()
        weather.observe(this@CurrentWeatherFragment, Observer{
            if(it == null) return@Observer
            group_loading.visibility = View.GONE
            updateDateToday()
            updatePrecipitation(it.precipitationVolume)
            updateTemp(it.temperature, it.feelsLikeTemperature)
            updateVisibility(it.visibilityDistance)
            updateWind(it.windSpeed, it.windDirection)
            updateCondition(it.conditionText)
            updateConditionImage(it.conditionIconUrl)
        })
    }
    private fun updateLocation(location: String){
        (activity as AppCompatActivity).supportActionBar?.title = location
    }
    private fun updateDateToday(){
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
    }
    @SuppressLint("SetTextI18n")
    private fun updateTemp(temperature : Double, feelslike : Double){
        val unitAbbreviation = chooseUnitLocalized("°C" , "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels Like $feelslike"
    }
    private fun updateCondition(condition: String){
        textView_condition.text = condition
    }
    private fun chooseUnitLocalized(metric:String, imperial:String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrecipitation(precipitationVolume:Double){
        val unitAbbreviation = chooseUnitLocalized("mm", "in")
        textView_precipitation.text = "$precipitationVolume $unitAbbreviation"
    }
    @SuppressLint("SetTextI18n")
    private fun updateWind(windSpeed :Double, windDirection:String){
        textView_wind.text = "Wind: $windSpeed $windDirection"
    }
    @SuppressLint("SetTextI18n")
    private fun updateVisibility(visibility:Double){
        val unitAbbreviation = chooseUnitLocalized("km", "mi")
        textView_visibility.text = "$visibility$unitAbbreviation"
    }
    private fun updateConditionImage(imageUrl:String){
        GlideApp.with(this@CurrentWeatherFragment).
            load("http:$imageUrl").into(imageView_condition_icon)
    }

}
