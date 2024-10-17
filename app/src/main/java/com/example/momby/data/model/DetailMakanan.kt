package com.example.momby.data.model

import com.google.gson.annotations.SerializedName

data class DetailMakanan(
    @SerializedName("Makanan Pokok") val makananPokok: String,
    var makananPokokIsDone:Boolean = false,

    @SerializedName("Pelengkap") val pelengkap: String,
    var pelengkapIsDone:Boolean = false,

    @SerializedName("Sayuran") val sayuran: String,
    var sayuranIsDone:Boolean = false,

    @SerializedName("Sumber Hewani") val sumberHewani: String,
    var sumberHewaniIsDone:Boolean = false,

    @SerializedName("Sumber Nabati") val sumberNabati: String,
    var sumberNabatiIsDone:Boolean = false,

    )