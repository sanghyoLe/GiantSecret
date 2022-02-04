package com.example.giantsecret.data.dao

import androidx.room.*
import com.example.giantsecret.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine):Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRoutine(routine: Routine, exercises: List<Exercise>):Long {
            val routineId = insertRoutine(routine)
        exercises.map {
            it.exerciseId?.let {
                insertRoutineExerciseCrossRef(RoutineExerciseCrossRef(routineId,it))
            }
        }
        return routineId
    }



    @Transaction
    @Query("SELECT * from routine where routineId = :id")
    suspend fun getRoutineWithExerciseById(id : Long) : RoutineWithExercises

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutineExerciseCrossRef(routineExerciseCrossRef: RoutineExerciseCrossRef)

    @Transaction
    @Query("SELECT * FROM Routine")
    suspend fun getRoutineWithExercises(): List<RoutineWithExercises>


    @Query("SELECT * FROM routine ORDER BY routineId DESC")
    fun getAllRoutine() : Flow<List<Routine>>

    @Delete
    suspend fun deleteRoutine(routine: Routine)

}


