package com.example.giantsecret.di

import android.content.Context
import com.example.giantsecret.data.dao.ExerciseDao
import com.example.giantsecret.data.dao.RoutineDao
import com.example.giantsecret.data.db.AppDatabase
import com.example.giantsecret.data.repository.ExerciseRepository
import com.example.giantsecret.data.repository.RoutineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(
        appContext,
        CoroutineScope(SupervisorJob())
    )

    @Singleton
    @Provides
    fun provideExerciseDao(db: AppDatabase) = db.exerciseDao()

    @Singleton
    @Provides
    fun provideExerciseRepository(localDataSource: ExerciseDao) = ExerciseRepository(localDataSource)

    @Singleton
    @Provides
    fun provideRoutineDao(db: AppDatabase) = db.routineDao()

    @Singleton
    @Provides
    fun provideRoutineExercise(localDataSource: ExerciseDao) = RoutineRepository(localDataSource)
}