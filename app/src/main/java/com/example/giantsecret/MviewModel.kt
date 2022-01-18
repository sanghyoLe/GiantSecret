package com.example.giantsecret

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.w3c.dom.Text
import kotlin.math.round

class MviewModel: ViewModel() {
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast
    private var oneRm = MutableLiveData<Float>()

    val oneRmData: LiveData<Float>
        get() = oneRm


    val repsText = MutableLiveData<String>()
    val weightText = MutableLiveData<String>()
    val nickNameText = MutableLiveData<String>()
    val benchPressText = MutableLiveData<String>()
    val squatText = MutableLiveData<String>()
    val deadLeftText = MutableLiveData<String>()
    val overHeadPressText = MutableLiveData<String>()


    init {
        oneRm.value = 0.00.toFloat()
    }

    fun getOneRm(){
        if(TextUtils.isEmpty(repsText.value) || TextUtils.isEmpty(weightText.value)){
            _showErrorToast.value = Event(true)
        } else {
            val calOneRm:Float = (weightText.value!!.toFloat() * (1 + (repsText.value!!.toFloat()/30)))
            oneRm.value = round(calOneRm)
        }
    }
    fun setInfo(){
        if(TextUtils.isEmpty(nickNameText.value)
            || TextUtils.isEmpty(benchPressText.value)
            || TextUtils.isEmpty(squatText.value)
            || TextUtils.isEmpty(deadLeftText.value)
            || TextUtils.isEmpty(overHeadPressText.value)
        ) {
            _showErrorToast.value = Event(true)
        } else {
            nickNameText.value?.let { App.prefs.setValue("nickName", it) }
            benchPressText.value?.let { App.prefs.setValue("benchPressWeight", it)}
            squatText.value?.let { App.prefs.setValue("squatWeight", it)}
            deadLeftText.value?.let { App.prefs.setValue("deadLeftWeight", it)}
            overHeadPressText.value?.let { App.prefs.setValue("overHeadPressWeight", it)}
        }
    }
    fun isHaveInformation() : Boolean {
        if(App.prefs.getValue("nickName") != null
            && App.prefs.getValue("benchPressWeight") != null
            && App.prefs.getValue("squatWeight") != null
            && App.prefs.getValue("deadLeftWeight") != null
            && App.prefs.getValue("overHeadPressWeight") != null) {
            return true
        }
        return false
    }

}