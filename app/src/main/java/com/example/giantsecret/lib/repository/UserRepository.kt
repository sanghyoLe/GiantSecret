package com.example.giantsecret.lib.repository

import androidx.annotation.WorkerThread
import com.example.giantsecret.lib.dao.UserDao
import com.example.giantsecret.lib.model.User


class UserRepository (private val userDao: UserDao) {


    val user: User = userDao.getUser()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }
}