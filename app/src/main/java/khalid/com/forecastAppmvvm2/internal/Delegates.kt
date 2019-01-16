package khalid.com.forecastAppmvvm2.internal

import kotlinx.coroutines.*

/**
 * Created by ${KhalidToak} on 11/26/2018.
 */
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}