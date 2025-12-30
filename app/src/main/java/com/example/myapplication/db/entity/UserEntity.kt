package com.example.myapplication.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey()
    val login: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "hashpass")
    val hashPass: String
)
