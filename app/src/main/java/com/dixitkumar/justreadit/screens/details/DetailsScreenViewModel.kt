package com.dixitkumar.justreadit.screens.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val repository: BookRepository) : ViewModel(){

    var author_books : List<Item> by mutableStateOf(listOf())
    var relatedBooks :  List<Item> by mutableStateOf(listOf())
    var isLoading : Boolean by mutableStateOf(true)
    suspend fun getBookInfo(bookId : String): Resource<Item>{
        return  repository.getBookInfo(bookId)
    }

    fun getAuthorList(query : String){
        viewModelScope.launch {
            isLoading = true
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response = repository.getBooks(query)){
                    is Resource.Success ->{
                        author_books = response.data!!
                        if(author_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Problem Getting Auther Realated Book")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG",query)
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","Exception Occured : ${e.message.toString()}")
            }
        }
    }
    fun getRelatedBook(query : String){
        viewModelScope.launch {
            isLoading = true
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response = repository.getBooks(query)){
                    is Resource.Success ->{
                        relatedBooks = response.data!!
                        if(relatedBooks.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Problem Getting Related Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG",query) }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","Exception Occured While Getting Books : ${e.message.toString()}")
            }
        }
    }
}