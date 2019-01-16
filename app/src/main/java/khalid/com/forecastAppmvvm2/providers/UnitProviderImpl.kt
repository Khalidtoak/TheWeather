package khalid.com.forecastAppmvvm2.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import khalid.com.forecastAppmvvm2.internal.UnitSystem

class UnitProviderImpl(context:Context) :PreferenceProvider(context), UnitProvider {
    private val s = "UNIT_SYSTEM"

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(s, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}