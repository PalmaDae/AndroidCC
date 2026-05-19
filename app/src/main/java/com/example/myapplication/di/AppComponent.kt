package com.example.myapplication.di

import com.example.myapplication.MainActivity
import com.example.myapplication.MyApplication
import com.example.myapplication.viewmodel.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(application: MyApplication)

    fun getSearchViewModelFactory(): SearchViewModelFactory
    fun getSearchViewModel(): SearchViewModel

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}