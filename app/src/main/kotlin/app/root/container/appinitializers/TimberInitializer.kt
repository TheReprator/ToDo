package app.root.container.appinitializers

import android.app.Application
import app.root.container.di.IS_DEBUG
import dev.root.baseKotlin.actions.Logger
import dev.root.baseUi.appinitializers.AppInitializer
import javax.inject.Inject
import javax.inject.Named

class TimberInitializer @Inject constructor(
    private val logger: Logger,
    @Named(IS_DEBUG) val isDebug: Boolean
) : AppInitializer {
    override fun init(application: Application) = logger.setup(isDebug)
}
