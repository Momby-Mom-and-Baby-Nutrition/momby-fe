package com.example.momby.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.momby.data.converter.DateConverter
import com.example.momby.data.converter.MenuOptimizedConverter
import com.example.momby.data.converter.NutrisiMenuConverter
import com.example.momby.data.dao.HistoryDao
import com.example.momby.data.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(
    DateConverter::class,
    MenuOptimizedConverter::class,
    NutrisiMenuConverter::class
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}