package com.example.giantsecret.data.model



import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.RequiresPermission
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*
import kotlin.math.E

@Entity
data class Routine (
    @PrimaryKey(autoGenerate = true) val routineId:Long?,
    val name: String,
)
@Entity
data class ExercisePart(
    @PrimaryKey(autoGenerate = true) val partId:Long,
    val name : String
)

@Entity(primaryKeys = ["routineId","partId"])
data class RoutineExercisePartCrossRef(
    val routineId: Long,
    val partId: Long
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
    @ColumnInfo var parentExerciseId: Long?,
    @ColumnInfo var numberOfRep:Int,
    @ColumnInfo var weight:Double
)



data class ExerciseWithSet(
    @Embedded var exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "parentExerciseId",
    )
    var exerciseSets: List<ExerciseSet>
)



data class RoutineWithExerciseParts(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "partId",
        associateBy = Junction(RoutineExercisePartCrossRef::class)
    )
    val parts: List<ExercisePart>
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


