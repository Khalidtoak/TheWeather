package khalid.com.forecastAppmvvm2.ui.weather.future.list

import android.annotation.SuppressLint
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import khalid.com.forecastAppmvvm2.R
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.MetricSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.UnitSpecificSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.internal.glide.GlideApp
import kotlinx.android.synthetic.main.future_list_weather_item.view.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * Created by ${KhalidToak} on 1/20/2019.
 */
class FutureListItem(val weatherEntry: UnitSpecificSimpleFutureWeatherEntry) : Item(){
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.apply {
           viewHolder.itemView.textView_condition.text = weatherEntry.conditionText
           val dateTimeFormatter = updateDate()
           viewHolder.itemView.textView_date.text = weatherEntry.date.format(dateTimeFormatter)
           val abbreviation = updateTempAbbreviation()
           viewHolder.itemView.textView_temperature.text = "{${weatherEntry.avgTemperature}$abbreviation}"

           GlideApp.with(this.containerView).load("http:" + weatherEntry.conditionIconUrl).
               into(viewHolder.itemView.imageView_condition_icon)
       }
    }

    override fun getLayout(): Int  = R.layout.future_list_weather_item

    private fun  ViewHolder.updateDate() : DateTimeFormatter{
          return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    }
    private fun ViewHolder.updateTempAbbreviation() : String{
        return if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C " else "°F"
    }
}