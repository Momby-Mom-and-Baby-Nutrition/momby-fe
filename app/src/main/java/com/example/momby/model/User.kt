package com.example.momby.model

import java.util.Date

data class User(
    var name: String? = "",
    var profilePict: String? = "",
    var weightBefore: Double? = 0.0,
    var weightAfter: Double? = 0.0,
    var height: Double? = 0.0,
    var age: Int? = 0,
    var gestatAge: Int? = 0,
    var birthDate: Date? = Date(),
    var calorieNeeds: Int? = 0,
    var carbNeeds: Int? = 0,
    var fatNeeds: Int? = 0,
    var proteinNeeds: Int? = 0
)
