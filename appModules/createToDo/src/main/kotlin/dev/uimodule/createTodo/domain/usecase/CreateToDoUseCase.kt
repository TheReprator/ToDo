package dev.uimodule.createTodo.domain.usecase

import dev.uimodule.createTodo.domain.repository.CreateToDoRepository
import javax.inject.Inject

class CreateToDoUseCase @Inject constructor(private val repository: CreateToDoRepository) {
    suspend operator fun invoke(description: String): Long {
        return repository.createToDo(description)
    }
}