package com.dixitkumar.justreadit.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitkumar.justreadit.model.MUser
import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeEmailInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth : FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading :LiveData<Boolean> = _loading

    fun CreateUserWithEmailAndPassword(username : String,email : String,password : String,confirmPassword : String,phone:String,login:()->Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    CreateUser(username = username.toString(),phone = phone)
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

    private fun CreateUser(username : String?,phone : String){
        val userId = auth.currentUser?.uid
        val user = MUser(
            userId = userId.toString(),
            userName =  username.toString(),
            userIconUrl = "",
            phone = phone.toString()
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
                        Log.d("TAG","Problem Occured While Sign In ")
                    }
                }
            }catch (e : Exception){
                Log.d("TAG","Sign In With Email And Password : ${e.localizedMessage.toString()}")
            }
        }
    }
}