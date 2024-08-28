package dev.uimodule.todoList.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.uimodule.todoList.data.LocalDataSource
import dev.uimodule.todoList.domain.repository.ToDoRepository

@InstallIn(ViewModelComponent::class)
@Module
class ToDoListModule {

    @Provides
    fun provideToDoRepository(dataSource: LocalDataSource): ToDoRepository = dataSource
}