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
    suspend fun insertRoutineExerciseCrossRef(routineExerciseCrossRef: RoutineExerciseCrossRef)

    @Transaction
    @Query("SELECT * FROM Routine")
    suspend fun getRoutineWithExercises(): List<RoutineWithExercises>


    @Query("SELECT * FROM routine")
    fun getAllRoutine() : Flow<List<Routine>>

    @Delete
    suspend fun deleteRoutine(routine: Routine)

}


