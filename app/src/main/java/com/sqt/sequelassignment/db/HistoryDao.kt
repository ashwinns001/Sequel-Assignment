package com.sqt.sequelassignment.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sqt.sequelassignment.model.History
import com.sqt.sequelassignment.model.SearchResultItem

@Dao
interface HistoryDao {
    @Query("SELECT * FROM searchresultitem")
    fun getAll(): List<SearchResultItem>


    @Insert
    fun insertAll( users: SearchResultItem)

    @Delete
    fun delete(user: SearchResultItem)
}
