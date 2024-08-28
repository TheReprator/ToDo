package dev.uimodule.searchTodo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.uimodule.searchTodo.data.LocalDataSource
import dev.uimodule.searchTodo.domain.repository.SearchToDoRepository

@InstallIn(ViewModelComponent::class)
@Module
class SearchToDoListModule {

    @Provides
    fun provideToDoRepository(dataSource: LocalDataSource): SearchToDoRepository = dataSource
}