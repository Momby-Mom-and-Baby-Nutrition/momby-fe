package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class OptimizeMenuResponse(
    val best_fit: Double,
    val execution_time_seconds: Double,
    val menu_optimized: MenuOptimized,
    val nutritional_get: NutrisiMenu,
    val nutritional_needs: KebutuhanNutrisi
)



