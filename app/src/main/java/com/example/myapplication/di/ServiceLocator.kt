package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.GameRepository
import kotlinx.coroutines.Dispatchers
import com.example.myapplication.data.UserRepository
import com.example.myapplication.db.InceptionDatabase
import com.example.myapplication.mapper.UserModelMapper

object ServiceLocator {

    private const val DB_NAME = "inception.db"

    private var inceptionDatabase: InceptionDatabase? = null

    private val userModelMapper = UserModelMapper()
    private val gameRepository = GameRepository(Dispatchers.IO)

    private val userRepository = UserRepository(
        mapper = userModelMapper,
        ioDispatcher = Dispatchers.IO
    )

    fun initDatabase(appCtx: Context) {
        if (inceptionDatabase == null) {
            inceptionDatabase = Room.databaseBuilder(
                appCtx,
                InceptionDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    fun getDatabase(): InceptionDatabase =
        inceptionDatabase ?: throw IllegalStateException("DB is not initialized")

    fun getUserRepository(): UserRepository = userRepository

    fun getGameRepository(): GameRepository = gameRepository
}