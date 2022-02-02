package com.example.giantsecret.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithExercises(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "routineBasicId"
    )
    val exercises: List<Exercise>
)
