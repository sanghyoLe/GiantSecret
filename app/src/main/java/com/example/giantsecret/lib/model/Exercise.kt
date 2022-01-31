package com.example.giantsecret.lib.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var exerciseId:Long?,
    var name: String,
    var numberOfSet: Int,
)


@Entity(foreignKeys = [
    ForeignKey(entity = Exercise::class,
    parentColumns = arrayOf("exerciseId"),
    childColumns = arrayOf("parentExerciseId"),
    onDelete = CASCADE)
        ]
)
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true) val setId: Long?,
    @ColumnInfo var parentExerciseId: Long?,
    @ColumnInfo var numberOfRep:Int,
    @ColumnInfo var weight:Double
)


//@Entity(tableName = "exercise_set")
//data class ExerciseSet(
//    @PrimaryKey(autoGenerate = true) val setId: Long?,
//    @ColumnInfo var parentExerciseId: Long?,
//    @ColumnInfo var numberOfRep:Int,
//    @ColumnInfo var weight:Double
//)


data class ExerciseWithSet(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "parentExerciseId"
    )
    val sets: List<ExerciseSet>
)
