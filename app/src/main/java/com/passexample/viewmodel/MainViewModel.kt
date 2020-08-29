package com.passexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.passexample.data.Pass
import com.passexample.data.PassType
import java.util.*

class MainViewModel : ViewModel() {
    private var expiredTime: Date = Date()
    private val _expiredDate = MutableLiveData<Date>()
    val expiredDate: LiveData<Date> = _expiredDate

    fun adjustExpirationDate(pass: Pass) {
        if (pass.activate) {
            if (pass.passType == PassType.DAY) {
                Calendar.getInstance().apply {
                    this.time = expiredTime
                    this.add(Calendar.DATE, pass.amount)
                    this[Calendar.SECOND] = 59
                    this[Calendar.MINUTE] = 59
                    this[Calendar.HOUR_OF_DAY] = 23
                    expiredTime = this.time
                }
            } else if (pass.passType == PassType.HOUR) {
                Calendar.getInstance().apply {
                    this.time = expiredTime
                    this.add(Calendar.HOUR, pass.amount)
                    expiredTime = this.time
                }

            }
        }
        _expiredDate.value = expiredTime
    }
}