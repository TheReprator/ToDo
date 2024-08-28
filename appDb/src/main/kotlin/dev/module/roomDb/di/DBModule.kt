package dev.module.roomDb.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.module.roomDb.AppDatabase
import dev.module.roomDb.di.impl.DiskExecutor
import dev.root.baseKotlin.actions.Logger
import javax.inject.Singleton

private const val APP_DB_NAME = "appDBToDo"

@InstallIn(SingletonComponent::class)
@Module
class DBModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context,
        logger: Logger,
        diskExecutor: DiskExecutor
    ) = Room.databaseBuilder(app, AppDatabase::class.java, APP_DB_NAME)
        .setQueryExecutor(diskExecutor)
        .setTransactionExecutor(diskExecutor)
        .setQueryCallback(queryCallback = { sqlQuery, bindArgs ->
            logger.e("SQl Query: $sqlQuery, bindArg: $bindArgs")
           },
            diskExecutor)
        .build()

    @Singleton
    @Provides
    fun provideNewsLocalDao(db: AppDatabase) = db.getDao()
}