package app.root.container.implementation

import dev.root.baseKotlin.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCoroutineDispatchersImpl @Inject constructor(
    override val main: CoroutineDispatcher,
    override val singleThread: CoroutineDispatcher,
    override val computation: CoroutineDispatcher = singleThread,
    override val io: CoroutineDispatcher = singleThread,
    override val default: CoroutineDispatcher = singleThread
) : AppCoroutineDispatchers
