package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DonorPointDetailDto
import com.example.myapplication.data.model.DonorPointDetailModel
import com.example.myapplication.data.model.DonorPointModel
import com.example.myapplication.data.network.RetrofitHelper
import com.example.myapplication.data.network.RetrofitHelper.api
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var points by mutableStateOf<List<DonorPointModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun load(cityName: String) {
        if (cityName.isBlank()) return

        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val resp = RetrofitHelper.api.getAllPoints(citySlug = cityName)
                if (resp.results.isEmpty()) {
                    error = "Пункты не найдены"
                    points = emptyList()
                } else {
                    points = resp.results.map { it.toModel() }
                }
            } catch (e: Exception) {
                error = "Ошибка загрузки: ${e.message}"
                points = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    suspend fun getDetail(pointId: Int): DonorPointDetailModel? {
        val dto: DonorPointDetailDto = api.getDetail(pointId)
        return dto.toDetailModel()
    }
}