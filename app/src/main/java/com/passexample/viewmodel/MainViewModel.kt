package com.passexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.passexample.data.Pass
import com.passexample.data.PassType

class MainViewModel : ViewModel() {

    private var totalTime: Long = System.currentTimeMillis()

    private val _expiredDate = MutableLiveData<Long>()
    val expiredDate: LiveData<Long> = _expiredDate


    fun adjustExpirationDate(pass: Pass) {
        if (pass.activate) {
            if (pass.passType == PassType.DAY) {
                totalTime += 24 * 60 * 60 * 1000 * pass.amount
            } else if (pass.passType == PassType.HOUR) {
                totalTime += 60 * 60 * 1000 * pass.amount
            }
        } else {
            if (pass.passType == PassType.DAY) {
                totalTime -= 24 * 60 * 60 * 1000 * pass.amount
            } else if (pass.passType == PassType.HOUR) {
                totalTime -= 60 * 60 * 1000 * pass.amount
            }
        }
        _expiredDate.value = totalTime
    }
}