package khalid.com.forecastAppmvvm2.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import khalid.com.forecastAppmvvm2.R
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.UnitSpecificSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.future_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureWeather : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory :FutureWeatherListViewModelFactory by instance()
    private lateinit var viewModel: FutureWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureWeatherViewModel::class.java)
        bindUi()
    }
    private fun bindUi() = launch(Dispatchers.Main) {
        val weatherLocation = viewModel.weatherLocation.await()
        val futureWeather = viewModel.weatherEntries.await()
        weatherLocation.observe(this@FutureWeather, Observer {location->
            if(location==null) return@Observer else updateLocation(location.name)
        })
        futureWeather.observe(this@FutureWeather, Observer { weatherEntries ->
            if (weatherEntries==null) return@Observer else
                group_loading.visibility ==View.GONE
                updateSubtitleToNextWeek()
            initRecyclerView(weatherEntries.toFutureListItem())
        })

    }
    private fun updateLocation(name:String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = name
    }
    private fun updateSubtitleToNextWeek(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =  "Next week"
    }
    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureListItem() : List<FutureListItem>{
        return this.map {
            FutureListItem(it)
        }
    }
    private fun initRecyclerView(items : List<FutureListItem>){
        val  groupAdapter = GroupAdapter<ViewHolder>().apply {
         addAll(items)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureWeather.context)
            adapter  = groupAdapter
        }
    }

}
