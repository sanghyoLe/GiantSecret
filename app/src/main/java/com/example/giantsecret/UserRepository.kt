package com.example.giantsecret

import androidx.annotation.WorkerThread
import com.example.giantsecret.db.dao.UserDao
import com.example.giantsecret.db.entity.User


class UserRepository (private val userDao:UserDao ) {


    val user:User = userDao.getUser()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }
}