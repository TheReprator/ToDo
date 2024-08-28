package dev.uimodule.searchTodo.fixtures

import androidx.paging.PagingData
import dev.uimodule.searchTodo.domain.modal.ModalToDo
import dev.uimodule.searchTodo.domain.repository.SearchToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource : SearchToDoRepository {

    companion object {
        val OUTPUT_ONE = ModalToDo(1L, "one item")
        val toDoListItem = listOf(OUTPUT_ONE, (ModalToDo(2L, "two")))
    }

    override fun getToDoPagingSource(query: String): Flow<PagingData<ModalToDo>> {
        val result = toDoListItem.filter {
            it.description.startsWith(query)
        }
        return flowOf(PagingData.from(result))
    }

}