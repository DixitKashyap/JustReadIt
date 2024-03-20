package com.dixitkumar.justreadit.screens.details

import androidx.lifecycle.ViewModel
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val repository: BookRepository) : ViewModel(){

    suspend fun getBookInfo(bookId : String): Resource<Item>{
        return repository.getBookInfo(bookId)
    }
}