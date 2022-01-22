package com.example.giantsecret

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        // SharedPreferences에 저장된 값이 있으면 livedata 초기화화
        App.prefs.getValue("nickName")?.let { nickName.value = it
            inputBtnText = "수정 완료"
            nickNameText.value = it
        }
        App.prefs.getValue("benchPressWeight")?.let {
            benchPressWeight.value = it
            benchPressText.value = it
            benchPressWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
        }
        App.prefs.getValue("squatWeight")?.let {
            squatWeight.value = it
            squatText.value = it
            squatWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
        }
        App.prefs.getValue("deadLiftWeight")?.let {
            deadLiftWeight.value = it
            deadLiftText.value = it
            deadLiftWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
        }
        App.prefs.getValue("overHeadPressWeight")?.let {
            overHeadPressWeight.value = it
            overHeadPressText.value = it
            overHeadPressWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
        }

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
                App.prefs.setValue("nickName", it)
                nickName.value = it
            }
            benchPressText.value?.let {
                App.prefs.setValue("benchPressWeight", it)
                benchPressWeight.value = it
                benchPressWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
            }
            squatText.value?.let {
                App.prefs.setValue("squatWeight", it)
                squatWeight.value = it
                squatWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
            }
            deadLiftText.value?.let {
                App.prefs.setValue("deadLiftWeight", it)
                deadLiftWeight.value = it
                deadLiftWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
            }
            overHeadPressText.value?.let {
                App.prefs.setValue("overHeadPressWeight", it)
                overHeadPressWeight.value = it
                overHeadPressWeightDoubleArray.value = createWeightDoubleArray(it.toDouble())
            }
            _showCompleteInputToast.value = Event(true)
        }
    }
    private fun createWeightDoubleArray(weight:Double) : DoubleArray {
        var doubleArray = DoubleArray(10)
        var temp = 0.5
        (0..9).forEach { i ->
            doubleArray[i] = 2.5*(Math.floor((weight*temp)/2.5))
            temp = (temp + 0.05)
        }
        return doubleArray
    }


}