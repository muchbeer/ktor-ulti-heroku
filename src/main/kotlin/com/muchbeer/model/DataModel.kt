package com.muchbeer.model

data class School(
    val id : Int,
    val school : String,
    val region : String,
    val sex : String
)

data class ImageUpload(
    val imageUrl : String,
    val fileName : String,
    val fileDescription : String
)
