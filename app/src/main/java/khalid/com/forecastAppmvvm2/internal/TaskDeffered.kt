package khalid.com.forecastAppmvvm2.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/**
 * Created by ${KhalidToak} on 12/26/2018.
 */
fun <T> Task<T>.asDeferred() : Deferred<T>{
    val deferred = CompletableDeferred<T>()
    this.addOnSuccessListener {
        deferred.complete(it)
    }
    this.addOnFailureListener{
        deferred.completeExceptionally(it)
    }
    return deferred
}