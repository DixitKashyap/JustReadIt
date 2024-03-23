package com.dixitkumar.justreadit.repository

import android.util.Log
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.network.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api : BookApi) {

    suspend fun getBooks(searchQuery : String) : Resource<List<Item>> {
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getAllBooks(searchQuery).items
            Log.d("TAG","Search Query Locked Query"+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getBookInfo(bookId:String) : Resource<Item>{
        val response = try{
            Resource.Loading(data = true)
            api.getBookInfo(bookId)
        }catch (e : Exception){
            Log.d("TAG","Having problem getting books"+e.localizedMessage)
            return Resource.Error(message = "An Error Occured ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return  Resource.Success(data = response)
    }
}