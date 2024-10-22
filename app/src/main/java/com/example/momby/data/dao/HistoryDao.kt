package com.example.momby.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.momby.data.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history ORDER BY date ASC")
    fun getAllHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE date = :specificDate ORDER BY date ASC")
    fun getHistoryByDate(specificDate: Long): HistoryEntity
}