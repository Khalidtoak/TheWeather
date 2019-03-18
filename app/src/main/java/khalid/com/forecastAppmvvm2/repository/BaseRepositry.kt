package khalid.com.forecastAppmvvm2.repository

import android.util.Log
import khalid.com.forecastAppmvvm2.data.response.network.Result
import retrofit2.Response
import java.io.IOException

/**
 * Created by ${KhalidToak} on 3/17/2019.
 */
open class BaseRepositry{
    suspend fun <T: Any> safeApiCall(call : suspend() -> Response<T>, errorMessage : String) : T?{
        val result = safeApiResult(call, errorMessage)
        var data : T? = null
        when(result){
            is Result.success ->
                data = result.data
            is Result.Error ->
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
        }
        return data
    }

    private suspend fun<T : Any> safeApiResult(call  : suspend() -> Response<T>, errorMessage: String )
            : Result<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Result.success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR " +
                "- $errorMessage"))

    }
}