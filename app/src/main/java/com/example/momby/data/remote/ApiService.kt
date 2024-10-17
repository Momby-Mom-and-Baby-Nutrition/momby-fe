package com.example.momby.data

import retrofit2.Response
import retrofit2.http.POST

interface ApiService {

    @POST("/optimize-menu")
    suspend fun getMenu(): Response<>
}