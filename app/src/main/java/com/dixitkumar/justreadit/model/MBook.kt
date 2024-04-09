package com.dixitkumar.justreadit.model

data class MBook(
    val bookId : String ="",
    val likedBy :Int = 0,
    val comments : List<Comments>? = null,
)