package dev.module.roomDb.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.module.roomDb.modals.DBEntityToDo

@Dao
interface DBDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(toDoList: List<DBEntityToDo>)

    @Insert
    suspend fun insert(toDo: DBEntityToDo): Long

    @Query("Select * From todo ORDER BY id DESC")
    fun getToDo(): DataSource.Factory<Int, DBEntityToDo>

    @Query("SELECT * FROM todo WHERE description LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchToDo(query: String):  DataSource.Factory<Int, DBEntityToDo>

    @Query("SELECT * FROM todo WHERE description LIKE '%' || :query || '%' ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun searchToDo(query: String, limit: Int, offset: Int): List<DBEntityToDo>
}