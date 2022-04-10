package pro.fateeva.mvpapp

import android.os.Handler

private data class Subscriber<T>(
    private val handler: Handler,
    private val callback: (T?) -> Unit
) {
    fun invoke(value: T?) {
        handler.post {
            callback.invoke(value)
        }
    }
}

class Publisher<T>() {
    private val subscribers: MutableSet<Subscriber<T?>> = mutableSetOf()

    var value: T? = null
        private set

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
        if (value != null) {
            subscriber.invoke(value)
        }
    }

    fun unsubscribeAll() {
        subscribers.clear()
    }

    fun post(value: T) {
        this.value = value
        subscribers.forEach {
            it.invoke(value)
        }
    }
}