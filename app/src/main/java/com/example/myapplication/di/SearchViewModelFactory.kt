package com.example.myapplication.di

import com.example.myapplication.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor() {
    fun createForDetail(pointId: Int): SearchViewModel {
        return SearchViewModel().apply {
            setCurrentPointId(pointId)
        }
    }
}