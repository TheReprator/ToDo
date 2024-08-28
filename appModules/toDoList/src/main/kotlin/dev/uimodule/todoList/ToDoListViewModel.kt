package dev.uimodule.todoList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.root.baseKotlin.actions.Logger
import dev.uimodule.todoList.domain.modal.ModalTodo
import dev.uimodule.todoList.domain.usecase.ToDoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    toDoUseCase: ToDoUseCase,
    private val logger: Logger,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var scrollPosition = 0
        private set

    val paginatedToDo: Flow<PagingData<ModalTodo>> = toDoUseCase()

    fun updateScrollPosition(scrollPosition: Int) {
        this.scrollPosition = scrollPosition
    }
}