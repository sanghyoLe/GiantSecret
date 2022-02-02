package com.example.giantsecret.data.repository

import androidx.annotation.WorkerThread
import com.example.giantsecret.data.dao.UserDao
import com.example.giantsecret.data.model.User


class UserRepository (private val userDao: UserDao) {


    val user: User = userDao.getUser()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }
}