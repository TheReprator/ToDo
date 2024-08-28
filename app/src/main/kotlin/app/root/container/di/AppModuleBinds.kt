package app.root.container.di

import app.root.container.appinitializers.TimberInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dev.root.baseUi.appinitializers.AppInitializer

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {
    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer
}
