package dev.uimodule.todoList.domain.usecase

import dev.uimodule.todoList.domain.repository.ToDoRepository
import javax.inject.Inject

class ToDoUseCase @Inject constructor(private val repository: ToDoRepository) {
    operator fun invoke() = repository.getToDoPagingSource()
}