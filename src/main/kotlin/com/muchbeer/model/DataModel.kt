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

data class ImageUpload(
    val imageUrl : String,
    val fileName : String,
    val fileDescription : String
)

data class SmsContent(
    val phone_number : String,
    val text_message : String
)

sealed class DataState<out R> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
    data class ErrorException(val exception: Exception) : DataState<Nothing>()
}