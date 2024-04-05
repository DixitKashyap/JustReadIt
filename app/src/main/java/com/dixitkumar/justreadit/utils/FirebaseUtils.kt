package com.dixitkumar.justreadit.utils

import android.util.Log
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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