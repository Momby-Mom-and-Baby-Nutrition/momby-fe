package com.example.momby.data.converter

import androidx.room.TypeConverter
import com.example.momby.data.model.NutrisiMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NutrisiMenuConverter {
    @TypeConverter
    fun fromNutrisiMenu(nutrisiMenu: NutrisiMenu?): String {
        return Gson().toJson(nutrisiMenu)
    }
    @TypeConverter
    fun toNutrisiMenu(nutrisiMenuString: String?): NutrisiMenu? {
        return Gson().fromJson(nutrisiMenuString, object : TypeToken<NutrisiMenu>() {}.type)
    }
}