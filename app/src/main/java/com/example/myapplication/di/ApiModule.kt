package com.example.myapplication.di

import com.example.myapplication.data.network.RetrofitHelper
import com.example.myapplication.data.network.api.DonorApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideDonorApi(): DonorApi = RetrofitHelper.api
}