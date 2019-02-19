package khalid.com.forecastAppmvvm2.ui.weather.future.list
import android.annotation.SuppressLint
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import khalid.com.forecastAppmvvm2.R
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.MetricSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.data.db.unitLocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import khalid.com.forecastAppmvvm2.internal.glide.GlideApp
import kotlinx.android.synthetic.main.future_list_weather_item.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * Created by ${KhalidToak} on 1/20/2019.
 */
class FutureListItem(val weatherEntry: UnitSpecificSimpleFutureWeatherEntry) : Item(){
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.apply {
           textView_condition.text = weatherEntry.conditionText
           updateDate()
           updateTemperature()
           updateConditionImage()
       }
    }

    override fun getLayout() = R.layout.future_list_weather_item

    private fun ViewHolder.updateDate() {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dtFormatter)
    }

    @SuppressLint("SetTextI18n")
    private fun ViewHolder.updateTemperature() {
        val unitAbbreviation = if (weatherEntry is MetricSimpleFutureWeatherEntry) "°C"
        else "°F"
        textView_temperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"
    }

    private fun ViewHolder.updateConditionImage() {
        GlideApp.with(this.containerView)
            .load("http:" + weatherEntry.conditionIconUrl)
            .into(imageView_condition_icon)
    }
}