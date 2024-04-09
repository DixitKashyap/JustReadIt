package com.dixitkumar.justreadit.utils

import android.util.Log
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


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

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        Log.d("TAG", "USER ID ${currentUser.toString()}")
        val listUser = viewModel.data.value?.data!!.toList().filter {
            it.userId.toString().contentEquals(currentUser)
        }
        if (listUser.isNotEmpty()) {
            val user = listUser[0]
            return user
        }
    }
    return null
}