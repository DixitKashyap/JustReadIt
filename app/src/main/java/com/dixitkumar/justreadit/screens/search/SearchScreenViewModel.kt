package com.dixitkumar.justreadit.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
class SearchScreenViewModel@Inject constructor(private val repository: BookRepository) : ViewModel() {
    var default_list : List<Item> by mutableStateOf(listOf())
    var suggestedList : List<Item> by mutableStateOf(listOf())
    var isLoading : Boolean by mutableStateOf(true)
    init {
        loadBooks()
    }
    suspend fun getBookInfo(bookId : String): Resource<Item>{
        return  repository.getBookInfo(bookId)
    }
    private fun loadBooks(){
        searchQuery("Flutter")
        suggested_book("query=Flutter")
    }
    fun searchQuery(query : String){
        viewModelScope.launch {
            isLoading = true
            if (query.isEmpty()) {
                return@launch
            }

            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        default_list = response.data!!
                        if (default_list.isNotEmpty()) isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Log.e("TAG", "Book Searching : Failed Getting Books")
                    }

                    else -> {
                        isLoading = false
                        Log.d("TAG", query)
                    }
                }
            } catch (e: Exception) {
                isLoading = false
                Log.d("TAG", "searchBooks : ${e.message.toString()}")
            }
        }
}
    fun suggested_book(query : String){
        viewModelScope.launch {
            isLoading = true
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response = repository.getBooks("query=${query}")){
                    is Resource.Success ->{
                        suggestedList = response.data!!
                        if(default_list.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG",query)
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
}

