package khalid.com.forecastAppmvvm2.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * Created by ${KhalidToak} on 12/25/2018.
 */
abstract class PreferenceProvider(context: Context){
    private val appContext = context.applicationContext
    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}