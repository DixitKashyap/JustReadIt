package com.dixitkumar.justreadit.screens.login

import android.os.Handler
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.utils.firebaseErrorMessage
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.play.integrity.internal.i
import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeEmailInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth : FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading :LiveData<Boolean> = _loading
    val error_message = mutableStateOf("")
    val alreadyThereName = mutableStateOf(false)
    val alreadyThereEmail = mutableStateOf(false)


    fun CreateUserWithEmailAndPassword(username : String,email : String,password : String,confirmPassword : String,phone:String,login:()->Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    CreateUser(username = username.toString(),phone = phone,email = email)
                    login()
                }else{

                    Log.d("TAG","Create User With Email And Password : ${it.result.toString()}")
                }
            }.addOnFailureListener {
                Log.d("TAG","Create User With Email And Password : ${it.localizedMessage.toString()}")
            }
            _loading.value =false
        }
    }

    private fun CreateUser(username : String?,phone : String,email: String){
        val userId = auth.currentUser?.uid
        val user = MUser(
            userId = userId.toString(),
            userName =  username.toString(),
            userIconUrl = "",
            phone = phone.toString(),
            email = email,
            likedBooks = emptyList(),
            readingList = emptyMap(),
            wishlist = emptyList(),
            finishedReading = emptyMap(),
            recentSearched = emptyList()
        )

        FirebaseFirestore.getInstance().collection("users").add(user)
    }

    fun SignInWithEmailAndPassword(email : String,password : String,login :()->Unit){
        viewModelScope.launch {
            try{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        login()
                        Log.d("TAG",it.result.toString())
                    }else{
                        val exception = it.exception as FirebaseAuthException
                        val errorMessage = firebaseErrorMessage(errorCode = exception.errorCode)
                        error_message.value = errorMessage
                        Log.d("TAG","Problem Occured While Sign In ")
                    }
                }
            }catch (e : Exception){
                error_message.value = e.localizedMessage.toString()

                Log.d("TAG","Sign In With Email And Password : ${e.localizedMessage.toString()}")
            }
        }
    }

    fun getUserName(userName : String = "",email: String = ""){
        val fireRef = FirebaseFirestore.getInstance()
        val users_collection = fireRef.collection("users")

        users_collection.get().addOnCompleteListener {
            for(document in it.result){
                if( userName.isNotEmpty() && userName.contentEquals(document.data["userName"].toString())){
                   alreadyThereName.value = true
                }
                if(email.isNotEmpty() && email.contentEquals(document.data["email"].toString())){
                    alreadyThereEmail.value = true
                }

            }
        }
    }

}


