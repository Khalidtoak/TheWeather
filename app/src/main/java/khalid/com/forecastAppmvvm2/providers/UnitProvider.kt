package khalid.com.forecastAppmvvm2.providers

import khalid.com.forecastAppmvvm2.internal.UnitSystem

/**
 * Created by ${KhalidToak} on 12/9/2018.
 */
interface UnitProvider{
    fun getUnitSystem(): UnitSystem
}