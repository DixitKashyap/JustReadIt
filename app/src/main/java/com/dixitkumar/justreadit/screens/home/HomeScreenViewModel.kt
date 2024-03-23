package com.dixitkumar.justreadit.screens.home

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
class HomeScreenViewModel @Inject constructor(private val repository: BookRepository) : ViewModel(){
    var default_list : List<Item> by mutableStateOf(listOf())
    var life_style_books :  List<Item> by mutableStateOf(listOf())
    var motivational_books :  List<Item> by mutableStateOf(listOf())
    var detective_novels :  List<Item> by mutableStateOf(listOf())
    var history_books :  List<Item> by mutableStateOf(listOf())
    var android_book :  List<Item> by mutableStateOf(listOf())
    var novels :  List<Item> by mutableStateOf(listOf())
    var manga :   List<Item> by mutableStateOf(listOf())
    var mystery_thriller_books :  List<Item> by mutableStateOf(listOf())
    var isLoading : Boolean by mutableStateOf(true)
    var autobiography : List<Item> by mutableStateOf(listOf())


    init {
        loadBooks()
        lifeStyleBook()
        motivationalBook()
        detectiveNovels()
        historyBook()
        androidBook()
        Novels()
        getMange()
        thrillerBooks()
        autobiographyBook()
    }

    private fun loadBooks(){
        searchQuery("Flutter")
    }

    private fun lifeStyleBook(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:health")){
                    is Resource.Success ->{
                        life_style_books = response.data!!
                        if(life_style_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
   private fun motivationalBook(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:self_help")){
                    is Resource.Success ->{
                        motivational_books = response.data!!
                        if(motivational_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
    private fun detectiveNovels(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:spirituality")){
                    is Resource.Success ->{
                        detective_novels = response.data!!
                        if(life_style_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
    private fun historyBook(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:history")){
                    is Resource.Success ->{
                        history_books = response.data!!
                        if(life_style_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }

   private fun androidBook() {
        viewModelScope.launch {
            isLoading = true
            try {
                when (val response = repository.getBooks("subject:computer")) {
                    is Resource.Success -> {
                        android_book = response.data!!
                        if (life_style_books.isNotEmpty()) isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Log.e("TAG", "Book Searching : Failed Getting Books")
                    }

                    else -> {
                        isLoading = false
                        Log.d("TAG", "Error While Loading the books")
                    }
                }
            } catch (e: Exception) {
                isLoading = false
                Log.d("TAG", "searchBooks : ${e.message.toString()}")
            }
        }
    }
   private fun Novels(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:novels")){
                    is Resource.Success ->{
                        novels= response.data!!
                        if(life_style_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
    private fun getMange(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:anime")){
                    is Resource.Success ->{
                        manga= response.data!!
                        if(life_style_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }

    private fun thrillerBooks(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("Mystery&Thriller")){
                    is Resource.Success ->{
                        mystery_thriller_books = response.data!!
                        if(life_style_books.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
    private fun autobiographyBook(){
        viewModelScope.launch {
            isLoading = true
            try{
                when(val response = repository.getBooks("subject:biography")){
                    is Resource.Success ->{
                       autobiography = response.data!!
                        if(autobiography.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error ->{
                        isLoading = false
                        Log.e("TAG","Book Searching : Failed Getting Books")
                    }
                    else ->{
                        isLoading = false
                        Log.d("TAG","Error While Loading the books")
                    }
                }
            }catch (e : Exception){
                isLoading = false
                Log.d("TAG","searchBooks : ${e.message.toString()}")
            }
        }
    }
    fun searchQuery(query : String){
        viewModelScope.launch {
            isLoading = true
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response = repository.getBooks(query)){
                    is Resource.Success ->{
                        default_list = response.data!!
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
