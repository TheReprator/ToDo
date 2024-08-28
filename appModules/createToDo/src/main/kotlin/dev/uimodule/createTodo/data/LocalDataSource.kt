package dev.uimodule.createTodo.data

import dev.module.roomDb.dao.DBDao
import dev.uimodule.createTodo.data.mapper.CreateToDoDbMapper
import dev.uimodule.createTodo.domain.repository.CreateToDoRepository
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val toDoDao: DBDao, private val mapper: CreateToDoDbMapper): CreateToDoRepository {

    override suspend fun createToDo(description: String): Long = toDoDao.insert(mapper.map(description))

}