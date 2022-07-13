package com.muchbeer.model

data class School(
    val id : Int,
    val school : String,
    val region : String,
    val sex : String
)

data class USSDModel(
    val sessionId : String,
    val phoneNumber : String,
    val networkCode : String,
    val serviceCode : String,
    val text : String
)