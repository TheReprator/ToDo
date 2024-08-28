package dev.module.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.module.roomDb.dao.DBDao
import dev.module.roomDb.modals.DBEntityToDo

@Database(
    entities = [DBEntityToDo::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): DBDao
}