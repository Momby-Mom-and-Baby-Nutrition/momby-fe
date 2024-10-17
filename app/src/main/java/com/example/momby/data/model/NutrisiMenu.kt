package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class NutrisiMenu(
    @SerializedName("Karbohidrat_gram") val karbohidrat: Double,
    @SerializedName("Lemak_gram") val lemak: Double,
    @SerializedName("Protein_gram") val protein: Double,
    @SerializedName("TEE_kalori") val teeKalori: Double
)