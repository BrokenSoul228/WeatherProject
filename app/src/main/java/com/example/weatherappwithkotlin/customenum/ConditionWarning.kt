package com.example.weatherappwithkotlin.customenum

class ConditionWarning {
    enum class Enu(s : String){
    Fog("Warning Fog"), Umbrella("Take an umbrella"), Drizzle("Warning Drizzle")
}
        fun getWeatherConditionWarning(countryCode : Int) : String {
            return when (countryCode) {
                45,48 -> Enu.Fog.toString()
                51,53,55,61,80,63,81,65,82,66,67,77,95,96,99 -> Enu.Umbrella.toString()
                56,57 -> Enu.Drizzle.toString()
                71,85 -> "Оденьтесь теплее"
                73 -> "деньтесь теплее"
                75,86 -> "деньтесь теплее"
                else -> "Погода хорошая"
            }
        }
    }