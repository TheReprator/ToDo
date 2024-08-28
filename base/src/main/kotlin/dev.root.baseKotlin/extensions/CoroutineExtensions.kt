package dev.root.baseKotlin.extensions

import dev.root.baseKotlin.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.mainBlock(
    coroutinesDispatcherProvider: AppCoroutineDispatchers,
    block: suspend CoroutineScope.() -> Unit
) {
    launch(coroutinesDispatcherProvider.main) {
        block(this)
    }
}

fun CoroutineScope.computationalBlock(
    coroutinesDispatcherProvider: AppCoroutineDispatchers,
    coroutineExceptionHandler: CoroutineExceptionHandler? = null,
    block: suspend CoroutineScope.() -> Unit
) {
    val type = coroutineExceptionHandler?.let {
        coroutinesDispatcherProvider.io + it
    } ?: coroutinesDispatcherProvider.io

    launch(type) {
        block.invoke(this)
    }
}
