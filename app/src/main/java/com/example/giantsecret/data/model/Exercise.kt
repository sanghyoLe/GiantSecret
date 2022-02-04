package com.example.giantsecret.data.model

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
    @PrimaryKey(autoGenerate = true) var setId: Long?,
    @ColumnInfo var parentExerciseId: Long?,
    @ColumnInfo var numberOfRep:Int,
    @ColumnInfo var weight:Double
)


data class ExerciseWithSet(
    @Embedded var exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "parentExerciseId"
    )
    var sets: List<ExerciseSet>
)
