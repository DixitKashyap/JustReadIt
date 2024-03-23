package com.dixitkumar.justreadit.network

import com.dixitkumar.justreadit.model.Book
import com.dixitkumar.justreadit.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface BookApi {

    @GET("volumes")
    suspend fun getAllBooks(@Query("q")query : String) : Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId : String): Item
}