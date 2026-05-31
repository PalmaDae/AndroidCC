package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DonorPointDetailModel
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.data.network.RetrofitHelper
import com.example.myapplication.ui.screen.SearchUiState
import com.example.myapplication.utils.AnalyticsLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetPointsByCityUseCase {
    suspend operator fun invoke(citySlug: String): List<DonorPointModel> {
        if (citySlug.isBlank()) return emptyList()
        val resp = RetrofitHelper.api.getAllPoints(citySlug = citySlug)
        return resp.results.map { it.toModel() }
    }
}

class GetPointDetailUseCase {
    suspend operator fun invoke(pointId: Int): DonorPointDetailModel? {
        if (pointId <= 0) return null
        return try {
            val dto = RetrofitHelper.api.getDetail(pointId)
            dto.toDetailModel()
        } catch (e: Exception) {
            null
        }
    }
}


class SearchViewModel @Inject constructor(
    private val analyticsLogger: AnalyticsLogger
) : ViewModel() {

    val getPointsByCityUseCase = GetPointsByCityUseCase()
    val getPointDetailUseCase = GetPointDetailUseCase()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()


    val points get() = _uiState.value.points
    val isLoading get() = _uiState.value.isLoading
    val error get() = _uiState.value.error
    val currentPointDetail get() = _uiState.value.currentPointDetail

    fun load(cityName: String) {
        if (cityName.isBlank()) return

        _uiState.update { state -> state.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val pointsList = getPointsByCityUseCase(cityName)
                if (pointsList.isEmpty()) {
                    _uiState.update { state ->
                        state.copy(points = emptyList(), isLoading = false, error = "Пункты не найдены")
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(points = pointsList, isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(points = emptyList(), isLoading = false, error = "Ошибка загрузки: ${e.message}")
                }
                analyticsLogger.logError("Error loading points for city: $cityName", e)
            }
        }
    }

    fun setCurrentPointId(pointId: Int) {
        viewModelScope.launch {
            val detail = getPointDetailUseCase(pointId)
            _uiState.update { state -> state.copy(currentPointDetail = detail) }
        }
    }
}