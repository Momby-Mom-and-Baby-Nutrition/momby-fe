package com.example.momby.data.converter

import androidx.room.TypeConverter
import com.example.momby.data.model.MenuOptimized
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class MenuOptimizedConverter {
    @TypeConverter
    fun fromMenuOptimized(menuOptimized: MenuOptimized?): String? {
        return Gson().toJson(menuOptimized)
    }

    @TypeConverter
    fun toMenuOptimized(menuOptimizedString: String?): MenuOptimized? {
        return Gson().fromJson(menuOptimizedString, object : TypeToken<MenuOptimized>() {}.type)
    }
}