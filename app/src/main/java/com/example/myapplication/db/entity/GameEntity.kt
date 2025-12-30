package com.example.myapplication.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_login")
    val userLogin: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "impressions")
    val impres: String
)
