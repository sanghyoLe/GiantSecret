package com.example.giantsecret.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION


@Entity
data class Routine (
    @PrimaryKey val id:Long,
    val name: String,
    var numberOfTime: Int,
    var exerciseParts: String,
    var exerciseIds: String

)


