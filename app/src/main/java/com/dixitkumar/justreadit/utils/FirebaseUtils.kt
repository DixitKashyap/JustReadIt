package com.dixitkumar.justreadit.utils

import android.util.Log
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.model.MBook
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth


fun firebaseErrorMessage(errorCode : String):String{
    Log.d("TAG",errorCode)
    return when(errorCode){
        "ERROR_USER_DISABLED" -> "Account is Disabled"
        "ERROR_INVALID_EMAIL" -> "Invalid Email Enter Valid Email Address."
        "ERROR_INVALID_CREDENTIAL" ->"Invalid Email or Password."

        else -> "Login Failed.Please Try Again"
    }
}

fun getCurrentUserId() : String{
    return FirebaseAuth.getInstance().currentUser?.uid.toString()
}

fun GetFirebaseUserData(viewModel: FirebaseViewModel) : MUser?{
    val currentUser = getCurrentUserId()
    val bookList: MutableList<Item> = mutableListOf()

    if (!viewModel.userData.value.data.isNullOrEmpty()) {
        Log.d("User Data", "USER ID ${currentUser.toString()}")
        val listUser = viewModel.userData.value?.data!!.toList().filter {
            it.userId.toString().contentEquals(currentUser)
        }
        if (listUser.isNotEmpty()) {
            val user = listUser[0]
            return user
        }
    }
    return null
}

fun GetFirebaseBookData(viewModel: FirebaseViewModel,bookId : String) : MBook?{
    if(!viewModel.bookData.value.data.isNullOrEmpty()){
        val bookList = viewModel.bookData.value.data!!.toList().filter {
            it.bookId == bookId
        }
        if(bookList.isNotEmpty()){
            val book = bookList[0]
            return book
        }
    }
    return null
}