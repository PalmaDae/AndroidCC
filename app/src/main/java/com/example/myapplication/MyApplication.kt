package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.ApplicationModule
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.utils.AnalyticsLogger
import com.example.myapplication.utils.UserManager
import javax.inject.Inject

class MyApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()


        val userId = appComponent.getUserManager().getUserId()
        appComponent.getAnalyticsLogger().setUserId(userId)
    }
}