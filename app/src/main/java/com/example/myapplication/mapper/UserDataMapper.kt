package com.example.myapplication.mapper

import com.example.myapplication.db.entity.UserEntity
import com.example.myapplication.model.UserDataModel
import com.example.myapplication.utils.HashUtil

class UserModelMapper {

    fun map(input: UserDataModel): UserEntity {
        return UserEntity(
            login = input.login,
            name = input.name,
            hashPass = HashUtil.hashPassword(input.password)
        )
    }

    fun map(input: UserEntity): UserDataModel {
        return UserDataModel(
            login = input.login,
            name = input.name,
            password = ""
        )
    }
}