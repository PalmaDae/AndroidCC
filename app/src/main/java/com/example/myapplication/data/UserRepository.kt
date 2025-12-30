package com.example.myapplication.data

import com.example.myapplication.db.entity.UserEntity
import com.example.myapplication.di.ServiceLocator
import com.example.myapplication.mapper.UserModelMapper
import com.example.myapplication.model.UserDataModel
import com.example.myapplication.utils.HashUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepository(
    private val mapper: UserModelMapper,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val userDao = lazy { ServiceLocator.getDatabase().userDao() }

    suspend fun createNewUser(userModel: UserDataModel) {
        withContext(ioDispatcher) {
            val entity: UserEntity = UserEntity(
                login = userModel.login,
                name = userModel.name,
                hashPass = HashUtil.hashPassword(userModel.password)
            )
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