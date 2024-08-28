package dev.uimodule.searchTodo.domain.repository

import androidx.paging.PagingData
import dev.uimodule.searchTodo.domain.modal.ModalToDo
import kotlinx.coroutines.flow.Flow

interface SearchToDoRepository {
    fun getToDoPagingSource(query: String): Flow<PagingData<ModalToDo>>
}