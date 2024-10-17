package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class DetailSnack(
    @SerializedName("Pelengkap") val pelengkap: String,
    var pelengkapIsDone:Boolean = false
)