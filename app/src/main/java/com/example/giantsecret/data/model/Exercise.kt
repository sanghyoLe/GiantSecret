package com.example.giantsecret.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlin.math.E

@Entity
data class Routine (
    @PrimaryKey(autoGenerate = true) val routineId:Long?,
    val name: String,
)

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo var exerciseId:Long?,
    @ColumnInfo var parentRoutineId: Long?,
    @ColumnInfo var name: String,
    @ColumnInfo var numberOfSet: Int,
)

@Entity
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true) var setId: Long?,
    @ColumnInfo var numberOfRep:Int,
    @ColumnInfo var weight:Double
)

data class ExerciseWithSet(
    @Embedded var exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "setId",
        associateBy = Junction(ExerciseSetCrossRef::class)
    )
    var exerciseSets: List<ExerciseSet>
)


@Entity(primaryKeys = ["exerciseId","setId"])
data class ExerciseSetCrossRef(
    val exerciseId: Long,
    val setId: Long
)

data class RoutineWithExerciseAndSets(
    @Embedded val routine: Routine,
    @Relation(
        entity = Exercise::class,
        parentColumn = "routineId",
        entityColumn = "parentRoutineId",
    )
    val exercise: List<ExerciseWithSet>
)


