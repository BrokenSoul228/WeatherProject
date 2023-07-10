package com.example.weatherappwithkotlin.customenum

class ConditionWarning {
        fun getWeatherConditionWarning(countryCode : Int) : String? {
            return when (countryCode) {
                45,48 -> "Аккуратней за рулем"
                51 -> "Возьмите зонт"
                53 -> "Возьмите зонт"
                55 -> "Возьмите зонт"
                56,57 -> "Изморось"
                61,80 -> "Возьмите зонт"
                63,81 -> "Возьмите зонт"
                65,82 -> "Возьмите зонт"
                66,67 -> "Возьмите зонт"
                71,85 -> "Оденьтесь теплее"
                73 -> "деньтесь теплее"
                75,86 -> "деньтесь теплее"
                77 -> "Возьмите зонт"
                95 -> "Возьмите зонт"
                96,99 -> "Возьмите зонт"
                else -> null
            }
        }
    }