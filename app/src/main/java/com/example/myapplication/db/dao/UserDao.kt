package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putUserData(user: UserEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateUserData(user: UserEntity)

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): UserEntity?

    @Query("DELETE FROM users WHERE login = :login")
    suspend fun deleteUserByLogin(login: String)
}