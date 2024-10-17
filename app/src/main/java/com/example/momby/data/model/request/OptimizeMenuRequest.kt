package com.example.momby.data.model.request

data class OptimizeMenuRequest(
    val height: Float,
    val weight: Float,
    val age: Int,
    val activity: Int,
    val gestation: Int
)