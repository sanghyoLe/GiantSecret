package com.example.giantsecret.viewModel

import androidx.lifecycle.*
import com.example.giantsecret.lib.repository.RoutineRepository
import com.example.giantsecret.lib.model.Routine
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RoutineViewModel(private val repository: RoutineRepository) : ViewModel(){

    val allRoutines: LiveData<List<Routine>> = repository.allRoutines.asLiveData()

    fun insert(routine: Routine) = viewModelScope.launch {
        repository.insert(routine)
    }
}

class RoutineViewModelFactory(private val repository: RoutineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RoutineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoutineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}