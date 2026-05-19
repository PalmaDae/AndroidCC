package com.example.myapplication.di

import com.example.myapplication.utils.AnalyticsLogger
import com.example.myapplication.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    private val analyticsLogger: AnalyticsLogger
) {
    fun createForDetail(pointId: Int): SearchViewModel {
        return SearchViewModel(analyticsLogger).apply {
            setCurrentPointId(pointId)
        }
    }
}