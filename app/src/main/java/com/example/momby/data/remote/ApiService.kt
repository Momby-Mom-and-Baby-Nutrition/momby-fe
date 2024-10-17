package com.example.momby.data.remote

import com.example.momby.data.model.request.OptimizeMenuRequest
import com.example.momby.data.model.response.OptimizeMenuResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/optimize-menu")
    suspend fun getMenu(
        @Body request: OptimizeMenuRequest
    ): OptimizeMenuResponse
}