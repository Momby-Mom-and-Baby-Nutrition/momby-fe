package com.example.momby.model

data class MenuMakan(
    val id: Int,
    val nama:String,
    val desc:String,
    var isDone:Boolean = false
)
