package com.passexample.data


class Pass(val passType: PassType, val amount: Int, val addTime: String, var activate: Boolean)

enum class PassType {
    DAY, HOUR
}