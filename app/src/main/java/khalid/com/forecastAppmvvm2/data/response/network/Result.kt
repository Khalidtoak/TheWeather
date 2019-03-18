package khalid.com.forecastAppmvvm2.data.response.network

import java.lang.Exception

/**
 * Created by ${KhalidToak} on 3/17/2019.
 */
sealed class Result<out T : Any>{
    data class success<out T : Any>(val data : T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}