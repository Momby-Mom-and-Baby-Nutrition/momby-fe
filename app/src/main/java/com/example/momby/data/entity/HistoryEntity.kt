package com.example.momby.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.momby.data.converter.DateConverter
import com.example.momby.data.converter.MenuOptimizedConverter
import com.example.momby.data.converter.NutrisiMenuConverter
import com.example.momby.data.model.MenuOptimized
import com.example.momby.data.model.NutrisiMenu

@Entity(tableName = "history",indices = [Index(value = ["userId", "date"], unique = true)])
@TypeConverters(
    NutrisiMenuConverter::class,
    DateConverter::class,
    MenuOptimizedConverter::class
)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    @TypeConverters(DateConverter::class)
    val date: Long,
    val description: String,


    @TypeConverters(MenuOptimizedConverter::class)
    val menuOptimized: MenuOptimized,


    @TypeConverters(NutrisiMenuConverter::class)
    val nutrisiMenu: NutrisiMenu
)