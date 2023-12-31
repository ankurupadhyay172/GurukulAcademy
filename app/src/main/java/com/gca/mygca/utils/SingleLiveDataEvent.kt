package com.gca.mygca.utils

import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * @Author Ankur Upadhyay
 * @Date 12-Fab-2023
 */
open class SingleLiveDataEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }


    fun value() = getContentIfNotHandled()

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}


/**
 * An [Observer] for [SingleLiveDataEvent]s, simplifying the pattern of checking if the [SingleLiveDataEvent]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [SingleLiveDataEvent]'s contents has not been handled.
 */
class SingleLiveObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<SingleLiveDataEvent<T>> {
    override fun onChanged(event: SingleLiveDataEvent<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}



