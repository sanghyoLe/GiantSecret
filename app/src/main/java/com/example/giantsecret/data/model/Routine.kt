package com.example.giantsecret.data.model

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION
import kotlinx.parcelize.Parcelize


@Entity
data class Routine (
    @PrimaryKey(autoGenerate = true) val routineId:Long?,
    val name: String,
    )
@Entity(primaryKeys = ["routineId","exerciseId"])
data class RoutineExerciseCrossRef(
    val routineId: Long,
    val exerciseId: Long
)

data class RoutineWithExercises(
    @Embedded val routine: Routine,
    @Relation(
       parentColumn = "routineId",
       entityColumn = "exerciseId",
       associateBy = Junction(RoutineExerciseCrossRef::class)
    )
    val exercises: List<Exercise>
)
//@Entity
//data class ExercisePart (
//    @PrimaryKey(autoGenerate = true) val exercisePartId:Long,
//    val name: String
//)
//
//@Entity(primaryKeys = ["routineId","exercisePartId"])
//data class RoutineExercisePartCrossRef(
//    val routineId: Long,
//    val exercisePartId: Long
//)
//data class RoutineWithExerciseParts(
//    @Embedded val routine: Routine,
//    @Relation(
//        parentColumn = "routineId",
//        entityColumn = "exercisePartId",
//        associateBy = Junction(RoutineExercisePartCrossRef::class)
//    )
//    val exerciseParts: List<ExercisePart>
//)