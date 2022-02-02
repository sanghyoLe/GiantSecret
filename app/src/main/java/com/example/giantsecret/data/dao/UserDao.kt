package com.example.giantsecret.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giantsecret.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
}