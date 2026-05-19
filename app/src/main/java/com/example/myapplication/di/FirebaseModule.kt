package com.example.myapplication.di

import android.app.Application
import android.content.Context
import com.example.myapplication.utils.AnalyticsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun provideAnalyticsLogger(firebaseAnalytics: FirebaseAnalytics): AnalyticsLogger {
        return AnalyticsLogger(firebaseAnalytics)
    }
}