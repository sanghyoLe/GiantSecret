package com.example.giantsecret.lib.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val UserId:Int,
    val name: String,
    val benchPressWeight: Double
)
