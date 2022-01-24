package com.example.giantsecret

import android.app.Application
import com.example.giantsecret.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RoutineApplication :Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this,applicationScope)}
    val repository by lazy { RoutineRepository(database.routineDao())}
}