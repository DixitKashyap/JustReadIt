package com.dixitkumar.justreadit.di

import com.dixitkumar.justreadit.network.BookApi
import com.dixitkumar.justreadit.repository.BookRepository
import com.dixitkumar.justreadit.repository.FireBaseBookRepository
import com.dixitkumar.justreadit.repository.FireBaseUserRepository
import com.dixitkumar.justreadit.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideBookApi(): BookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookRepository(api : BookApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideFireUserRepository(): FireBaseUserRepository {
        return FireBaseUserRepository(queryBook = FirebaseFirestore.getInstance().collection("users"))
    }

    @Singleton
    @Provides
    fun provideFireBookRepository(): FireBaseBookRepository {
        return FireBaseBookRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))
    }

}