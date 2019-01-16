package khalid.com.forecastAppmvvm2.data.response.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import khalid.com.forecastAppmvvm2.data.response.CurrentWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.FutureWeatherResponse
import khalid.com.forecastAppmvvm2.data.response.network.API_KEY
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ${KhalidToak} on 11/12/2018.
 */
const val API_KEY = "6f41967d7bf14644b01142434181211"
interface WeatherApiService {
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location : String
    ) : Deferred<CurrentWeatherResponse>
    //https://api.apixu.com/v1/forecast.json?key=6f41967d7bf14644b01142434181211&q=Lagos&days=3
    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q")location: String
    , @Query("days") days: Int
    ) : Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor) : WeatherApiService {
            val requestInterceptor = Interceptor{
                val url = it.request()
                    .url()
                    .newBuilder().addQueryParameter("key", API_KEY)
                    .build()
                val request = it.request()
                    .newBuilder().url(url)
                    .build()
                return@Interceptor it.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(WeatherApiService::class.java)
        }
    }
}