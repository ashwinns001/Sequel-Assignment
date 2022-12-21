package com.sqt.sequelassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sqt.sequelassignment.model.SearchResultItem

@Database(entities = [SearchResultItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historydao(): HistoryDao
}
