package com.example.giantsecret

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MviewModel: ViewModel() {
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast
    private var oneRm = MutableLiveData<Float>()

    val oneRmData: LiveData<Float>
        get() = oneRm


    val repsText = MutableLiveData<String>()
    val weightText = MutableLiveData<String>()

    init {
        oneRm.value = 0.00.toFloat()
    }

    fun getOneRm(){

        if(TextUtils.isEmpty(repsText.value) || TextUtils.isEmpty(weightText.value)){
            _showErrorToast.value = Event(true)
        } else {
            val calOneRm:Float = repsText.value!!.toFloat() + weightText.value!!.toFloat()
            oneRm.value = calOneRm
        }

    }

}