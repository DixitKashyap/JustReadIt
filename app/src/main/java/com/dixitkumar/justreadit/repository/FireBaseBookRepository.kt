package com.dixitkumar.justreadit.repository

import android.util.Log
import com.dixitkumar.justreadit.data.DataOrException
import com.dixitkumar.justreadit.model.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseBookRepository@Inject constructor(private val queryBook : Query){

    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>,Boolean,Exception>()

        try{
            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            Log.d("TAG","Books ${dataOrException.data}")
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        }catch (e : FirebaseFirestoreException){
            dataOrException.e = e
        }
        return dataOrException
    }
}