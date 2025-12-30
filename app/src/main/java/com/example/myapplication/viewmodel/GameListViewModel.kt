package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class SortType { NAME, RATING, STATUS }
sealed class GameListUiState {
    data object Loading : GameListUiState()
    data class Success(val games: List<GameEntity>) : GameListUiState()
}

class GameListViewModel : ViewModel() {
    private val gameRepository = ServiceLocator.getGameRepository()
    private val currentLogin = UserDataRepository.getCurrentLogin() ?: ""

    private val _sortType = MutableStateFlow(SortType.NAME)
    val sortType: StateFlow<SortType> = _sortType

    val uiState: StateFlow<GameListUiState> = gameRepository.getGamesForUser(currentLogin)
        .combine(_sortType) { list, sort ->
            val sortedList = when (sort) {
                SortType.NAME -> list.sortedBy { it.title }
                SortType.RATING -> list.sortedByDescending { it.rating }
                SortType.STATUS -> list.sortedBy { it.status }
            }
            GameListUiState.Success(sortedList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GameListUiState.Loading
        )

    fun setSortType(type: SortType) {
        _sortType.value = type
    }
}