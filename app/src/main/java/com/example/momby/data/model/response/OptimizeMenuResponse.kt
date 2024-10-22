package com.example.momby.data.model.response

import com.example.momby.data.model.KebutuhanNutrisi
import com.example.momby.data.model.MenuOptimized
import com.example.momby.data.model.NutrisiMenu

data class OptimizeMenuResponse(
    val best_fit: Double,
    val menu_optimized: MenuOptimized,
    val nutritional_get: NutrisiMenu,
    val nutritional_needs: KebutuhanNutrisi
)



