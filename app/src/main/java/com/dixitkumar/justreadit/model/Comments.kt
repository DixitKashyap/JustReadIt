package com.dixitkumar.justreadit.model

data class Comments(
    val userId : String = "",
    val userName : String = "",
    val commentTitle : String= "",
    val commentContent : String ="",
    val time : String = "",
    val rating : Int = 0
)
