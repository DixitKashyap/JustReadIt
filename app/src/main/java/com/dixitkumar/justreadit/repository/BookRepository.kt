package com.dixitkumar.justreadit.repository

import android.content.res.Resources
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

    suspend fun getLifeStyleBooks() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getLifeStyle("LifeStyle").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getMotivationalBooks() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getMotivational("Motivational").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getFictionBooks() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getFictional("Fictional").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }
    suspend fun getHistoryBooks() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getHistory("History").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }
    suspend fun getComics() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getComics("Comics").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }
    suspend fun getAndroidBooks() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getAndroid("Android").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
            if(itemList.isNotEmpty())Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e : Exception){
            Resource.Error(message = e.message.toString())
        }
    }
    suspend fun getFinance() : Resource<List<Item>>{
        return try{
            Resource.Loading(data = true)
            val itemList  =api.getFinance("Finance").items
            Log.d("TAG","Life Style Regarding Book "+itemList.toString())
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
            return Resource.Error(message = "An Error Occured ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return  Resource.Success(data = response)
    }
}