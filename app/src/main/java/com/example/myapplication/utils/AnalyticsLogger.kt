package com.example.myapplication.utils

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsLogger @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun setUserId(userId: String) {
        FirebaseCrashlytics.getInstance().setUserId(userId)
        firebaseAnalytics.setUserId(userId)
        Log.d("Analytics", "User ID set: $userId")
    }

    fun logScreenView(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        FirebaseCrashlytics.getInstance().log("Screen viewed: $screenName")
        Log.d("Analytics", "Screen: $screenName")
    }

    fun logNavigation(fromScreen: String, toScreen: String, param: String? = null) {
        val message = "Navigation: $fromScreen -> $toScreen${param?.let { " with param: $it" } ?: ""}"
        FirebaseCrashlytics.getInstance().log(message)
        Log.d("Analytics", message)
    }

    fun logError(error: String, throwable: Throwable? = null) {
        FirebaseCrashlytics.getInstance().log(error)
        throwable?.let { FirebaseCrashlytics.getInstance().recordException(it) }
        Log.e("Analytics", error, throwable)
    }
}