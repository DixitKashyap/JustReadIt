package com.dixitkumar.justreadit.model

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)