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

    @Query("SELECT * FROM history WHERE userId = :userId ORDER BY date ASC")
    fun getAllHistoryByUid(userId: String): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE date = :specificDate AND userId = :userId ORDER BY date ASC")
    fun getHistoryByDateAndUid(specificDate: Long, userId: String): HistoryEntity
}