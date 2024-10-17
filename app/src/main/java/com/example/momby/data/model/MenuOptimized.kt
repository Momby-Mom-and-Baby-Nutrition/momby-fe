package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class MenuOptimized(
    @SerializedName("Makan Malam") var MakanMalam: DetailMakanan,
    @SerializedName("Makan Pagi") var MakanPagi: DetailMakanan,
    @SerializedName("Makan Siang") var MakanSiang: DetailMakanan,
    @SerializedName("Snack Pagi") var SnackPagi: DetailSnack,
    @SerializedName("Snack Sore") var SnackSore: DetailSnack
)