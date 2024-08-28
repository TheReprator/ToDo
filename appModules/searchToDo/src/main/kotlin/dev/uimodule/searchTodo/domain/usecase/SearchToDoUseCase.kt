package dev.uimodule.searchTodo.domain.usecase

import dev.uimodule.searchTodo.domain.repository.SearchToDoRepository
import javax.inject.Inject

class SearchToDoUseCase @Inject constructor(private val repository: SearchToDoRepository){
    operator fun invoke(search: String) = repository.getToDoPagingSource(search)
}