package com.example.giantsecret.data.model

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION
import kotlinx.parcelize.Parcelize





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