package com.example.myapplication.data.network

import com.example.myapplication.data.network.api.DonorApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object RetrofitHelper {
    private const val BASE_URL = "https://api2.donorsearch.org/api/"

    val api: DonorApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DonorApi::class.java)
    }
}