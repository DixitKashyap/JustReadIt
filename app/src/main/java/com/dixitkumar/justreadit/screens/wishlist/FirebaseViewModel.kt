package com.dixitkumar.justreadit.screens.wishlist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitkumar.justreadit.data.DataOrException
import com.dixitkumar.justreadit.model.Comments
import com.dixitkumar.justreadit.model.MBook
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.repository.FireBaseBookRepository
import com.dixitkumar.justreadit.repository.FireBaseUserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val userRepository : FireBaseUserRepository,
    private val bookRepository: FireBaseBookRepository
) : ViewModel() {

    val db = FirebaseFirestore.getInstance()
    val userData : MutableState<DataOrException<List<MUser>, Boolean, Exception>> = mutableStateOf(
        DataOrException(listOf(),true,Exception(""))
    )
    val bookData : MutableState<DataOrException<List<MBook>, Boolean, Exception>> = mutableStateOf(
        DataOrException(listOf(),true,Exception(""))
    )


    init {
        getAllUsersFromDatabase()
        getAllBooksFromDatabase()
    }

    private fun getAllUsersFromDatabase(){
        viewModelScope.launch {
            userData.value .loading = false
            userData.value = userRepository.getAllUsersFromDatabase()

            if(!userData.value.data.toString().isNullOrEmpty()) userData.value.loading = false
        }
        Log.d("TAG","getAllUserFromDatabase : ${userData.value.data?.toList()}")

    }

    private fun getAllBooksFromDatabase(){
        viewModelScope.launch {
            bookData.value.loading = false
            bookData.value = bookRepository.getAllBooksFromDatabase()

            if(!bookData.value.data.toString().isNullOrEmpty()) userData.value.loading = false
        }
        Log.d("Books" ,"getAllBooksFromDatabase : ${bookData.value.data?.toList()}")
    }

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

    fun getFieldAsList(collectionName: String, documentId: String, fieldName: String, callback: (MutableList<Any>?)-> Unit)  {
            db.collection(collectionName)
                .document(documentId)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val docRef = it.getResult()
                        if (docRef.exists()) {
                            val contentList = docRef.get(fieldName) as? MutableList<Any>?
                            if (contentList != null) {
                                callback(contentList)
                            }
                        } else {
                            Log.d(
                                "TAG",
                                "Error Occured While Retreving field Data ${it.result.data.toString()}"
                            )
                        }
                    }
        }
    }
    fun getFieldAsMap(collectionName: String, documentId: String, fieldName: String, callback: (MutableMap<String,String>)-> Unit)  {
        db.collection(collectionName)
            .document(documentId)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val docRef = it.getResult()
                    if (docRef.exists()) {
                        val contentList = docRef.get(fieldName) as? MutableMap<String,String>
                        if (contentList != null) {
                            callback(contentList)
                        }
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

    fun addBooksToCollection(bookId : String = "",bookComments : List<Comments>?= null){
        val myBooks = MBook(
            bookId = bookId,
            comments = bookComments
        )
        FirebaseFirestore.getInstance().collection("books").add(myBooks)
    }



}