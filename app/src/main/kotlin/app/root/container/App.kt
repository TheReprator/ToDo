package app.root.container

import android.app.Application
import app.root.container.appinitializers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(){

    @Inject
    lateinit var initializers: AppInitializers

    @Inject

    override fun onCreate() {
        super.onCreate()

        initializers.init(this)
    }
}
