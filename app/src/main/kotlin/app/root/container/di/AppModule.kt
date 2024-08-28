package app.root.container.di

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.paging.PagingConfig
import app.root.container.BuildConfig
import app.root.container.implementation.AppCoroutineDispatchersImpl
import app.root.container.implementation.AppLogger
import app.root.container.implementation.DateUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.root.baseKotlin.actions.DateUtils
import dev.root.baseKotlin.actions.Logger
import dev.root.baseKotlin.util.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

const val IS_DEBUG = "isDebug"

private const val PAGE_SIZE = 15

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            prefetchDistance = 3 * PAGE_SIZE,
            initialLoadSize = 2 * PAGE_SIZE,
        )
    }
    @Named(IS_DEBUG)
    @Provides
    fun provideIsDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    fun provideLogger(bind: AppLogger): Logger = bind


    @Provides
    fun provideLifeCycle(): Lifecycle {
        return ProcessLifecycleOwner.get().lifecycle
    }

    @Provides
    fun providesCoroutineScope(appCoroutineDispatchers: AppCoroutineDispatchers): CoroutineScope {
        return CoroutineScope(SupervisorJob() + appCoroutineDispatchers.default)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun provideCoroutineDispatcherProvider(): AppCoroutineDispatchers {
        return AppCoroutineDispatchersImpl(main = Dispatchers.Main,singleThread= Dispatchers.IO.limitedParallelism(1),
            computation=Dispatchers.IO.limitedParallelism(4), default=Dispatchers.Default, io = Dispatchers.IO)
    }

    @Provides
    fun provideDateUtils(): DateUtils {
        return DateUtilsImpl()
    }
}
