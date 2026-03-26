package com.example.myapplication.data.network.api

import com.example.myapplication.data.model.DonorResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DonorApi {
    @GET("blood_stations/")
    suspend fun getBloodStations(
        @Query("city") city: String
    ): DonorResponse
}