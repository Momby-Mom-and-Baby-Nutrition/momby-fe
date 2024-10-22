package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class DetailMakanan(
    @SerializedName("Makanan1") val makanan1: String,
    var makanan1IsDone:Boolean = false,

    @SerializedName("Makanan2") val makanan2: String,
    var makanan2IsDone:Boolean = false,

    @SerializedName("Makanan3") val makanan3: String,
    var makanan3IsDone:Boolean = false,
    )