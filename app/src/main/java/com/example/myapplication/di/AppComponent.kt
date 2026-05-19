package com.example.myapplication.di

import com.example.myapplication.MainActivity
import com.example.myapplication.MyApplication
import com.example.myapplication.utils.AnalyticsLogger
import com.example.myapplication.utils.UserManager
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, FirebaseModule::class, ApplicationModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(application: MyApplication)

    fun getSearchViewModelFactory(): SearchViewModelFactory
    fun getSearchViewModel(): SearchViewModel
    fun getAnalyticsLogger(): AnalyticsLogger
    fun getUserManager(): UserManager

    @Component.Builder
    interface Builder {
        fun applicationModule(module: ApplicationModule): Builder
        fun build(): AppComponent
    }
}