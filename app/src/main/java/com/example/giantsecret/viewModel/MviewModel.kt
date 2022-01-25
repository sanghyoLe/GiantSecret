package com.example.giantsecret.viewModel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giantsecret.Event
import kotlin.math.round

class MviewModel: ViewModel() {
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast
    private val _showCompleteInputToast = MutableLiveData<Event<Boolean>>()
    val showCompleteInputToast: LiveData<Event<Boolean>> = _showCompleteInputToast

    private var oneRm = MutableLiveData<Float>()
    private var nickName = MutableLiveData<String>()
    private var benchPressWeight = MutableLiveData<String>()
    private var squatWeight = MutableLiveData<String>()
    private var deadLiftWeight = MutableLiveData<String>()
    private var overHeadPressWeight = MutableLiveData<String>()


    private var benchPressWeightDoubleArray = MutableLiveData<DoubleArray>()
    private var squatWeightDoubleArray = MutableLiveData<DoubleArray>()
    private var deadLiftWeightDoubleArray = MutableLiveData<DoubleArray>()
    private var overHeadPressWeightDoubleArray = MutableLiveData<DoubleArray>()


    val oneRmData: LiveData<Float>
        get() = oneRm

    val nickNameData: LiveData<String>
        get() = nickName

    val benchPressWeightData: LiveData<String>
        get() = benchPressWeight

    val squatWeightData: LiveData<String>
        get() = squatWeight

    val deadLiftWeightData: LiveData<String>
        get() = deadLiftWeight

    val overHeadPressWeightData: LiveData<String>
        get() = overHeadPressWeight

    var inputBtnText: String = "입력 완료"


    val repsText = MutableLiveData<String>()
    val weightText = MutableLiveData<String>()

    val nickNameText = MutableLiveData<String>()
    val benchPressText = MutableLiveData<String>()
    val squatText = MutableLiveData<String>()
    val deadLiftText = MutableLiveData<String>()
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
            || TextUtils.isEmpty(deadLiftText.value)
            || TextUtils.isEmpty(overHeadPressText.value)
        ) {
            _showErrorToast.value = Event(true)
        } else {
            nickNameText.value?.let {
                nickName.value = it
            }
            benchPressText.value?.let {
                benchPressWeight.value = it
            }
            squatText.value?.let {
                squatWeight.value = it
            }
            deadLiftText.value?.let {
                deadLiftWeight.value = it
            }
            overHeadPressText.value?.let {
                overHeadPressWeight.value = it
            }
            _showCompleteInputToast.value = Event(true)
        }
    }



}