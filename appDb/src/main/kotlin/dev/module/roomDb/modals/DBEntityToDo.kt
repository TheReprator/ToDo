package dev.module.roomDb.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class DBEntityToDo(
    @PrimaryKey
    val id: Long = System.currentTimeMillis(),
    val description: String
)