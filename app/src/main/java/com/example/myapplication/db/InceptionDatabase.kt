package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.db.dao.GameDao
import com.example.myapplication.db.dao.UserDao
import com.example.myapplication.db.entity.GameEntity
import com.example.myapplication.db.entity.UserEntity

@Database(entities = [UserEntity::class, GameEntity::class], version = 4, exportSchema = false)
abstract class InceptionDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao


}