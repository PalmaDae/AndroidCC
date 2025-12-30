package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(game: GameEntity)

    @Query("SELECT * FROM games WHERE user_login = :login ORDER BY id DESC")
    fun getGamesForUser(login: String): Flow<List<GameEntity>>

    @Delete
    suspend fun deleteGame(game: GameEntity)

    @Query("SELECT * FROM games WHERE user_login = :login ORDER BY title ASC")
    fun getGamesSortedByTitle(login: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE user_login = :login ORDER BY rating DESC")
    fun getGamesSortedByRating(login: String): Flow<List<GameEntity>>
}