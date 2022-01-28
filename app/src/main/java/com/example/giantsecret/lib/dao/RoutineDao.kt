package com.example.giantsecret.lib.dao

import androidx.room.*
import com.example.giantsecret.lib.model.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
//    @Transaction
//    @Query("SELECT * FROM Routine")
//    suspend fun getAllRoutines(): List<RoutineWithExercises>
//
//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun createRoutine(routine: Routine, exercises: List<Exercise>) {
//        val routineId = insertRoutine(routine)
//        exercises.map {
//            it.apply { routineBasicId = routineId }
//        }.also { insertExercises(it) }
//    }
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertRoutine(routine: Routine) : Int
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertExercises(exercises: List<Exercise>)


        @Query("SELECT * FROM Routine")
        fun getAllRoutines(): Flow<List<Routine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertRoutine(routine: Routine)


}

