package com.uu.attendance.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineUtil {
    fun CoroutineScope.launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: (suspend CoroutineScope.(Throwable) -> Unit) = {},
        finallyBlock: (suspend CoroutineScope.() -> Unit) = {}
    ): Job {
        val handler = CoroutineExceptionHandler { _, throwable ->
            launch {
                if (throwable !is CancellationException) {
                    catchBlock(throwable)
                }
                finallyBlock()
            }
        }

        return launch(context + handler, start) {
            tryBlock()
            finallyBlock()
        }
    }

    suspend fun <T> withMain(block: suspend CoroutineScope.() -> T) =
        withContext(Dispatchers.Main, block)

    suspend fun <T> withIO(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)

//    fun ViewModel.launch(
//        context: CoroutineContext = EmptyCoroutineContext,
//        start: CoroutineStart = CoroutineStart.DEFAULT,
//        block: suspend CoroutineScope.() -> Unit
//    ) = viewModelScope.launch(context, start, tryBlock = block, catchBlock = {
//        if (BuildConfig.DEBUG) {
//            throw it
//        }
//        //ignore
//    })
}