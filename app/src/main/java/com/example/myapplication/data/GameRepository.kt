package com.example.myapplication.data

import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.di.ServiceLocator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GameRepository(
    private val ioDispatcher: CoroutineDispatcher
) {
    private val gameDao = lazy { ServiceLocator.getDatabase().gameDao() }

    val allGames: Flow<List<GameEntity>> = gameDao.value.getAllGames()

    suspend fun addGame(game: GameEntity) {
        withContext(ioDispatcher) {
            gameDao.value.addGame(game)
        }
    }

    suspend fun deleteGame(game: GameEntity) {
        withContext(ioDispatcher) {
            gameDao.value.deleteGame(game)
        }
    }
}