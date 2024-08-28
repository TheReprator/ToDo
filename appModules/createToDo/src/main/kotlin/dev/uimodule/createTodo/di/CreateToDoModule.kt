package dev.uimodule.createTodo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.uimodule.createTodo.data.LocalDataSource
import dev.uimodule.createTodo.domain.repository.CreateToDoRepository

@InstallIn(ViewModelComponent::class)
@Module
class CreateToDoModule {

    @Provides
    fun provideCreateToDoRepository(dataSource: LocalDataSource): CreateToDoRepository = dataSource
}