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
    private fun getUserDao() = ServiceLocator.getDatabase().userDao()

    suspend fun createNewUser(userModel: UserDataModel) {
        withContext(ioDispatcher) {
            val existingUser = getUserDao().getUserByLogin(userModel.login)
            if (existingUser != null) {
                throw Exception("User with this login already exists")
            }

            val entity = UserEntity(
                login = userModel.login,
                name = userModel.name,
                hashPass = HashUtil.hashPassword(userModel.password),
                deletionDate = null
            )
            getUserDao().putUserData(entity)
        }
    }

    suspend fun getUserByLogin(login: String): UserDataModel? {
        return withContext(ioDispatcher) {
            val entity = getUserDao().getUserByLogin(login)
            entity?.let { mapper.map(it) }
        }
    }

    suspend fun markAccountForDeletion(login: String) {
        withContext(ioDispatcher) {
            val user = getUserDao().getUserByLogin(login)
            user?.let {
                getUserDao().putUserData(it.copy(deletionDate = System.currentTimeMillis()))
            }
        }
    }

    suspend fun restoreAccount(login: String) {
        withContext(ioDispatcher) {
            val user = getUserDao().getUserByLogin(login)
            user?.let {
                getUserDao().putUserData(it.copy(deletionDate = null))
            }
        }
    }

    suspend fun permanentDelete(login: String) {
        withContext(ioDispatcher) {
            getUserDao().deleteUserByLogin(login)
        }
    }
}