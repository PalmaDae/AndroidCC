package com.example.myapplication.navigation
import kotlinx.serialization.Serializable

@Serializable
data object Login

@Serializable
data object Registration

@Serializable
data object GameList

@Serializable
data object AddGame

@Serializable
data object Profile

@Serializable
data class RestoreAccount(val login: String)