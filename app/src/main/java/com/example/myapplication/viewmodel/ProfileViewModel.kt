package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.data.UserDataRepository
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.navigation.Login
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val gameRepository = ServiceLocator.getGameRepository()
    private val userRepository = ServiceLocator.getUserRepository()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        val login = UserDataRepository.getCurrentLogin() ?: ""

        viewModelScope.launch {
            val user = userRepository.getUserByLogin(login)

            gameRepository.getGamesForUser(login).collect { games ->
                _uiState.value = ProfileUiState.Success(
                    name = user?.name ?: "Unknown",
                    login = login,
                    playingCount = games.count { it.status == "Playing" },
                    plannedCount = games.count { it.status == "Planned" },
                    completedCount = games.count { it.status == "Completed" }
                )
            }
        }
    }

    fun logout(navController: NavController) {
        UserDataRepository.clearSession()
        navController.navigate(Login) {
            popUpTo(0) { inclusive = true }
        }
    }
}

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(
        val name: String,
        val login: String,
        val playingCount: Int,
        val plannedCount: Int,
        val completedCount: Int
    ) : ProfileUiState()
}