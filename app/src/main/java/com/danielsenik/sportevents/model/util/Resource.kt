package com.danielsenik.sportevents.model.util

sealed class Resource<T>(
    val data: T? = null,
    val error: Exception? = null
) {
    class OK<T>(data: T? = null) : Resource<T>(data)
    class LOADING<T>() : Resource<T>()
    class ERROR<T>(data: T? = null, exception: Exception? = null) : Resource<T>(data, exception)
}
