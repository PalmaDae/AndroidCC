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
    fun addGame(game: GameEntity)

    @Query("SELECT * FROM games ORDER BY  id DESC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Delete
    fun deleteGame(game: GameEntity)

    @Query("SELECT * FROM games ORDER BY title ASC")
    fun getGamesSortedByTitle(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games ORDER BY rating DESC")
    fun getGamesSortedByRating(): Flow<List<GameEntity>>
}