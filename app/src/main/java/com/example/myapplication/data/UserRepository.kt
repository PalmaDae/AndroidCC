package com.example.myapplication.data

import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.mapper.UserModelMapper
import com.example.myapplication.model.UserDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepository(
    private val mapper: UserModelMapper,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val userDao = lazy { ServiceLocator.getDatabase().userDao() }

    suspend fun createNewUser(userData: UserDataModel) {
        withContext(ioDispatcher) {
            val entity = mapper.map(input = userData)
            userDao.value.putUserData(entity)
        }
    }

    suspend fun getUserByLogin(login: String): UserDataModel? {
        return withContext(ioDispatcher) {
            val entity = userDao.value.getUserByLogin(login)
            entity?.let { mapper.map(it) }
        }
    }
}