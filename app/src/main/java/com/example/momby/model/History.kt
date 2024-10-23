package com.example.momby.model

import com.example.momby.data.model.MenuOptimized
import java.util.Date

data class History(
    val menuOptimized: MenuOptimized?,
    val date: Date?
)