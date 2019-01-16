package khalid.com.forecastAppmvvm2.data.response.network

import android.content.Context
import android.net.ConnectivityManager
import khalid.com.forecastAppmvvm2.internal.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context): ConnectivityInterceptor {
    private  val appcontext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoNetworkException()
        return chain.proceed(chain.request())
    }
    private fun isOnline(): Boolean
    {
        val connectivityManager = appcontext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo !=null && networkInfo.isConnected
    }
}