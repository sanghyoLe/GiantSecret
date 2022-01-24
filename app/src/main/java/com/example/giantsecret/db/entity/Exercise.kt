package com.example.giantsecret.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Int,
    @ColumnInfo var routineBasicId: Int,
    @ColumnInfo var name: String?,
    @ColumnInfo var numberOfSet: Int,
    @ColumnInfo var numberOfRep: Int,
    @ColumnInfo var weight:Double
)
