package fr.maximeaeva.pocketscoins

class Movement (
    val id: Int = 1,
    var description: String = "None",
    var module: Int = 0,
    var add: Boolean = false,
    var value: Double = 0.0,
    val date: String = "2021-11-20 18:00:00.000"
)