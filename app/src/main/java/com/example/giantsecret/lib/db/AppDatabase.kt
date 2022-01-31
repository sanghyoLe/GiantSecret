package com.example.giantsecret.lib.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.giantsecret.lib.dao.ExerciseDao
import com.example.giantsecret.lib.dao.RoutineDao
import com.example.giantsecret.lib.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Routine::class, ExerciseSet::class,Exercise::class], version = 1 , exportSchema = true)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao
    abstract fun exerciseDao(): ExerciseDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var routineDao = database.routineDao()
                    var exerciseDao = database.exerciseDao()


                    var routine = Routine("hi")
                    routineDao.insertRoutine(routine)
                }
            }
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : AppDatabase {
            // INSTACE가 null이 아닐시, INSTACE 리턴
            // null 일시, database 생성
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database.db"
                ).addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}