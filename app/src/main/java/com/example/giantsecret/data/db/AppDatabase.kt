package com.example.giantsecret.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.giantsecret.data.dao.RoutineDao
import com.example.giantsecret.data.dao.RecordDao
import com.example.giantsecret.data.model.*
import com.example.giantsecret.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@TypeConverters(Converters::class)
@Database(entities = [
    Routine::class,
    ExerciseSet::class,
    Exercise::class,
    ExercisePart::class,
    RoutineExercisePartCrossRef::class,
    Record::class], version = 1 , exportSchema = true)
public abstract class AppDatabase : RoomDatabase() {


    abstract fun routineDao(): RoutineDao
    abstract fun recordDao(): RecordDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    insertParts(database)
                }
            }
        }
        suspend fun insertParts(db:AppDatabase){
            var exerciseDao = db.routineDao()
            exerciseDao.insertExercisePart(ExercisePart(1,"가슴"))
            exerciseDao.insertExercisePart(ExercisePart(2,"등"))
            exerciseDao.insertExercisePart(ExercisePart(3,"어깨"))
            exerciseDao.insertExercisePart(ExercisePart(4,"이두"))
            exerciseDao.insertExercisePart(ExercisePart(5,"삼두"))
            exerciseDao.insertExercisePart(ExercisePart(6,"하체"))
            exerciseDao.insertExercisePart(ExercisePart(7,"복부"))
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