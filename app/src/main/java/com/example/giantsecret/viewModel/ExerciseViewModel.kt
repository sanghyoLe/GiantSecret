package com.example.giantsecret.viewModel

import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.example.giantsecret.data.model.Exercise
import com.example.giantsecret.data.model.ExerciseSet
import com.example.giantsecret.data.model.ExerciseWithSet
import com.example.giantsecret.data.model.Routine
import com.example.giantsecret.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val exerciseWithSetFlow: LiveData<List<ExerciseWithSet>> = exerciseRepository.exerciseWithSetFlow.asLiveData()
    val exerciseFlow: LiveData<List<Exercise>> = exerciseRepository.exerciseFlow.asLiveData()



    init {

    }




    fun getExerciseWithSetByParentId(id:Long) : ExerciseWithSet {
        lateinit var exerciseWithSet: ExerciseWithSet
        viewModelScope.launch {
            exerciseWithSet = exerciseRepository.getExerciseWithSetByParentId(id)
        }
        return exerciseWithSet
    }

    fun createExercise(exercise: Exercise, sets: List<ExerciseSet>):Long {
        var createdId: Long = 0
        viewModelScope.launch {
            createdId = exerciseRepository.createExercise(exercise, sets)
        }

        return createdId
    }



    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }


    fun insertSets(set: List<ExerciseSet>) = viewModelScope.launch {
        exerciseRepository.insertSets(set)
    }

    fun insertSet(set: ExerciseSet) = viewModelScope.launch {
        exerciseRepository.insertSet(set)
    }


}






