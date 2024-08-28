package dev.uimodule.todoList.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.module.roomDb.dao.DBDao
import dev.uimodule.todoList.data.mapper.ToDoDbMapper
import dev.uimodule.todoList.domain.modal.ModalTodo
import dev.uimodule.todoList.domain.repository.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val pageConfig: PagingConfig,
    private val dbDao: DBDao,
    private val mapper: ToDoDbMapper
) : ToDoRepository {

    override fun getToDoPagingSource(): Flow<PagingData<ModalTodo>> {

        val pager = Pager(
            config = pageConfig,
            pagingSourceFactory = dbDao.getToDo().map { item ->
                mapper.map(item)
            }.asPagingSourceFactory()
        )

        return pager.flow.cachedIn(coroutineScope)
    }
}