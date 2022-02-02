package com.example.giantsecret.data.dao

import androidx.room.*
import com.example.giantsecret.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine):Long

    @Query("SELECT * FROM routine")
    fun getAllRoutine() : Flow<List<Routine>>

}


