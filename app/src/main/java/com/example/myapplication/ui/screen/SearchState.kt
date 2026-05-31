package com.example.myapplication.ui.screen

import androidx.compose.runtime.Immutable
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.data.model.DonorPointDetailModel

@Immutable
data class SearchUiState(
    val points: List<DonorPointModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPointDetail: DonorPointDetailModel? = null
)