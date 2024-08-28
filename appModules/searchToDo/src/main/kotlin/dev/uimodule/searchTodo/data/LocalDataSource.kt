package dev.uimodule.searchTodo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.module.roomDb.dao.DBDao
import dev.uimodule.searchTodo.data.mapper.ToDoDbMapper
import dev.uimodule.searchTodo.domain.modal.ModalToDo
import dev.uimodule.searchTodo.domain.repository.SearchToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val pageConfig: PagingConfig,
    private val dbDao: DBDao,
    private val mapper: ToDoDbMapper
) : SearchToDoRepository {

    override fun getToDoPagingSource(query: String): Flow<PagingData<ModalToDo>> {

        val pager = Pager(
            config = pageConfig,
            pagingSourceFactory = dbDao.searchToDo(query).map { item ->
                mapper.map(item)
            }.asPagingSourceFactory()
        )

        return pager.flow.cachedIn(coroutineScope)
    }
}