package com.alex_podolian.stackexchangetestapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

typealias AsyncCollector<T> = (T) -> Unit

interface Closeable {
    fun close()
}

class StateAsyncWrapper<Output>(private val originalFlow: StateFlow<Output>) {
    val sync: StateFlow<Output>
        get() = originalFlow

    val value: Output
        get() = sync.value

    fun async(onEach: AsyncCollector<Output>): Closeable =
        originalFlow.asyncCollect(onEach)
}

class SharedAsyncWrapper<Output>(private val originalFlow: SharedFlow<Output>) {
    val sync: SharedFlow<Output>
        get() = originalFlow

    fun async(onEach: AsyncCollector<Output>): Closeable =
        originalFlow.asyncCollect(onEach)
}


fun <T> SharedFlow<T>.asyncCollect(onEach: AsyncCollector<T>): Closeable {
    val collectionScope = CoroutineScope(Job() + Dispatchers.Main)
    onEach { onEach(it) }
        .launchIn(collectionScope)
    return object : Closeable {
        override fun close() {
            collectionScope.cancel()
        }
    }
}