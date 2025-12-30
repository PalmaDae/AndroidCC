package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

enum class SortType {
    NAME, RATING, STATUS
}

class GameListViewModel : ViewModel() {
    private val gameRepository = ServiceLocator.getGameRepository()
    private val currentLogin = UserDataRepository.getCurrentLogin() ?: ""

    private val _sortType = MutableStateFlow(SortType.NAME)
    val sortType: StateFlow<SortType> = _sortType

    val games: StateFlow<List<GameEntity>> = gameRepository.getGamesForUser(currentLogin)
        .combine(_sortType) { list, sort ->
            when (sort) {
                SortType.NAME -> list.sortedBy { it.title }
                SortType.RATING -> list.sortedByDescending { it.rating }
                SortType.STATUS -> list.sortedBy { it.status }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setSortType(type: SortType) {
        _sortType.value = type
    }
}