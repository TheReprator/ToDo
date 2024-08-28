package dev.uimodule.searchTodo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.uimodule.searchTodo.domain.modal.ModalToDo
import dev.uimodule.searchTodo.domain.usecase.SearchToDoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

const val SEARCH_TEXT = "toDoSearchQuery"

@HiltViewModel
class SearchTodoViewModel @Inject constructor(
    private val useCase: SearchToDoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(savedStateHandle.get<String>(SEARCH_TEXT))
    val toDoSearchText = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResults: Flow<PagingData<ModalToDo>> =  _searchQuery.debounce(2000)
        .filterNotNull()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest { query ->
            useCase(query)
        }

    fun updateSearch(query: String) {
        savedStateHandle[SEARCH_TEXT] = query
        this._searchQuery.value = query
    }
}