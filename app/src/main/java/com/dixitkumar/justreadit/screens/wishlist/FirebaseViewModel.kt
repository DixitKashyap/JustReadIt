package com.dixitkumar.justreadit.screens.wishlist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitkumar.justreadit.data.DataOrException
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.repository.FireRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(private val repository : FireRepository) : ViewModel() {

    val db = FirebaseFirestore.getInstance()
    val data : MutableState<DataOrException<List<MUser>, Boolean, Exception>> = mutableStateOf(
        DataOrException(listOf(),true,Exception(""))
    )

    fun updateField(collectionName: String, documentId: String, fieldName: String, newValue: Any) {
        db.collection(collectionName)
            .document(documentId)
            .update(fieldName, newValue)
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    Log.d("TAG", "Successufully Updated")
                }else{
                    Log.d("TAG",it.result.toString())
                }
            }
            .addOnFailureListener { e ->
                Log.d("TAG",e.localizedMessage.toString())
            }
    }
    fun getField(collectionName: String,documentId: String,fieldName: String,callback: (MutableList<String>?) -> Unit)  {
            db.collection(collectionName)
                .document(documentId)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val docRef = it.getResult()
                        if (docRef.exists()) {
                            val contentList = docRef.get(fieldName) as? MutableList<String>
                            callback(contentList)
                        } else {
                            Log.d(
                                "TAG",
                                "Error Occured While Retreving field Data ${it.result.data.toString()}"
                            )
                        }
                    }
        }
    }
    fun getDocumentReference(collectionName: String, fieldName: String, fieldValue: Any, callback: (String?) -> Unit) {
        val query = db.collection(collectionName).whereEqualTo(fieldName, fieldValue)
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Assuming there's only one matching document
                    val documentId = querySnapshot.documents[0].id
                    callback(documentId)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                // Handle failure (e.g., show an error message)
                callback(null)
            }
    }


    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase(){
        viewModelScope.launch {
            data.value .loading = false
            data.value = repository.getAllBooksFromDatabase()

            if(!data.value.data.toString().isNullOrEmpty()) data.value.loading = false
        }
        Log.d("TAG","getAllBooksFromDatabase : ${data.value.data?.toList().toString()}")

    }
}