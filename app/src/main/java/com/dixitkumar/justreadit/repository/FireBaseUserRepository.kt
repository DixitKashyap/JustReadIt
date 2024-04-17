package com.dixitkumar.justreadit.repository

import android.util.Log
import com.dixitkumar.justreadit.data.DataOrException
import com.dixitkumar.justreadit.model.MBook
import com.dixitkumar.justreadit.model.MUser
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseUserRepository @Inject constructor(private val queryBook : Query){

    suspend fun getAllUsersFromDatabase(): DataOrException<List<MUser>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MUser>,Boolean,Exception>()

        try{
            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MUser::class.java)!!
            }
            Log.d("TAG","FIRE ${dataOrException.data}")
            if(!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        }catch (e : FirebaseFirestoreException){
            dataOrException.e = e
        }
        return dataOrException
    }
}