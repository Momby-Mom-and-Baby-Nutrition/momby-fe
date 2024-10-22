package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class DetailSnack(
    @SerializedName("Snack") val snack: String,
    var snackIsDone:Boolean = false
)