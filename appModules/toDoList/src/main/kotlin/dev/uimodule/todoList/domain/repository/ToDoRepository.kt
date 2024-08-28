package dev.uimodule.todoList.domain.repository

import androidx.paging.PagingData
import dev.uimodule.todoList.domain.modal.ModalTodo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getToDoPagingSource(): Flow<PagingData<ModalTodo>>
}