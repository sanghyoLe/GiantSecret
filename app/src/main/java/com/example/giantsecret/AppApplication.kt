package com.example.giantsecret

import android.app.Application
import com.example.giantsecret.lib.db.AppDatabase
import com.example.giantsecret.lib.repository.ExerciseRepository
import com.example.giantsecret.lib.repository.RoutineRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AppApplication :Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this,applicationScope)}
    val repository by lazy { RoutineRepository(database.routineDao()) }
    val exerciseRepository by lazy { ExerciseRepository(database.exerciseDao())}

}