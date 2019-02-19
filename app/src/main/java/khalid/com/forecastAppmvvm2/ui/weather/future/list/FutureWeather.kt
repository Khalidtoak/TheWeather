package khalid.com.forecastAppmvvm2.ui.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import khalid.com.forecastAppmvvm2.R
import khalid.com.forecastAppmvvm2.data.db.LocalDateConverter
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.future_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate

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
        val futureWeather = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.weatherLocation.await()
        weatherLocation.observe(this@FutureWeather, Observer {location->
            if(location==null) return@Observer
            updateLocation(location.name)
        })
        futureWeather.observe(this@FutureWeather, Observer { weatherEntries ->
            if (weatherEntries==null) return@Observer
            group_loading.visibility = View.GONE
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
            Log.d("items", items.toString())
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureWeather.context)
            adapter  = groupAdapter
        }
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureListItem)?.let {
                showWeatherDetail(it.weatherEntry.date, view)
            }
        }
    }
    private fun showWeatherDetail(date : LocalDate, view: View){
        val dateString = LocalDateConverter.dateToSring(date)!!
        val actionDetail = FutureWeatherDirections.actionDetail(dateString)
        Navigation.findNavController(view).navigate(actionDetail)

    }

}
