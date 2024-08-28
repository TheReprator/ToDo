package dev.uimodule.createTodo.domain.repository

interface CreateToDoRepository {
    suspend fun createToDo(description: String): Long
}