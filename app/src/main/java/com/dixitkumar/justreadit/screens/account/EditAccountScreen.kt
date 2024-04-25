package com.dixitkumar.justreadit.screens.account

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.details.SubmitButton
import com.dixitkumar.justreadit.screens.details.TextInputFiled
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditAccountScreen(navController: NavController){
  Surface(color=Color.White) {
      EditAccountScreenUi(navController)
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditAccountScreenUi(navController: NavController,firebaseViewModel: FirebaseViewModel= hiltViewModel()){
    val user = GetFirebaseUserData(firebaseViewModel)
    val selectedImageUri = remember{ mutableStateOf<Uri?>(null) }

    val singlePhotosPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {result->
        selectedImageUri.value = result

    }
    val userId = user?.userId.toString()
    val userName = remember{ mutableStateOf("") }
    val userEmail = remember{ mutableStateOf("") }
    val userMobile = remember{ mutableStateOf("") }

    userName.value = user?.userName.toString()
    userEmail.value = user?.email.toString()
    userMobile.value = user?.phone.toString()
    selectedImageUri.value = user?.userIconUrl?.toUri()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())){
    TopNavigation(navController)
     Card (modifier = Modifier
         .fillMaxWidth()
         .padding(10.dp),
         colors = CardDefaults.cardColors(containerColor = Color.White),
         shape = RectangleShape,
         border = BorderStroke(width = 1.dp, color = Color.LightGray)
     ){
         //Image Option Header
         Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
         Row (modifier = Modifier.padding(15.dp)){
             Column (horizontalAlignment = Alignment.CenterHorizontally){
                 Text(text = "Photo",
                     fontSize = 22.sp,
                     fontWeight = FontWeight.Bold,
                     color = Color.Black)

                 Spacer(modifier = Modifier.height(6.dp))
                 Text(text = "Add a nice photo of yourself for your profile",
                     fontSize = 15.sp,
                     fontWeight = FontWeight.W400,
                 )
             }
         }
         HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

         //Image Selection Area
         Card(modifier = Modifier
             .padding(10.dp),
             border = BorderStroke(width = 1.dp, color = Color.LightGray),
             colors = CardDefaults.cardColors(containerColor = Color.White)
            ){
             Image(painter = if(selectedImageUri.value!=null) rememberAsyncImagePainter(selectedImageUri.value) else painterResource(R.drawable.user_icon),
                 contentDescription = "User Image",
                 contentScale = ContentScale.Fit,
                 modifier = Modifier
                     .height(200.dp)
                     .width(350.dp)
                     .fillMaxSize())
         }
         Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
             Button(onClick = {
                 singlePhotosPickerLauncher.launch(
                     PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                 )
             },
                 colors = ButtonDefaults.buttonColors(Color.White),
                 border = BorderStroke(width = 1.dp,Color.LightGray),
                 shape = RoundedCornerShape(10.dp),
                 modifier = Modifier.padding(10.dp)
             ) {
                Text(text = "Upload Image",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Black
                    )
             }
             Icon(imageVector =Icons.Default.Save ,
                 contentDescription = "Save Image Button",
                 tint = Color.Gray)
         }
         TextContentArea(userName,
             userEmail,
             userMobile,
             firebaseViewModel
         ){
             selectedImageUri.value?.let {
                 val storageRef = FirebaseStorage.getInstance().reference
                 val imageRef = storageRef.child("images/${LocalTime.now()}")
                 val uploadTask = imageRef.putFile(it)

                 uploadTask.addOnSuccessListener { taskSnapshot ->
                     // Image upload successful
                      taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                          firebaseViewModel.getDocumentReference("users","userId",userId){result->
                              firebaseViewModel.updateField("users",result.toString(),"userIconUrl",it.toString())
                              navController.navigate(route = ReaderScreens.AccountScreen.name){
                                  popUpTo(route = ReaderScreens.AccountScreen.name){
                                      inclusive = true
                                  }
                              }
                          }
                     }
                 }.addOnFailureListener { e ->
                     // Image upload failed
                     Log.d("IMAGE", "Image upload failed: $e")
                 }
             }
         }
       }
     }
   }
}

@Composable
fun TopNavigation(navController: NavController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.ArrowBack ,
            contentDescription = "Back Button",
            modifier = Modifier
                .weight(0.6f)
                .wrapContentWidth(Alignment.Start).clickable {
                    navController.popBackStack()
                })

        Text(text = "User Account",
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start))
    }
}


@Composable
fun TextContentArea(userName: MutableState<String>,
                    userEmail : MutableState<String>,
                    userMobile:MutableState<String>,
                    firebaseViewModel: FirebaseViewModel,
                    onClick:()->Unit
){


    val keyboardController = LocalSoftwareKeyboardController.current
    val userId = getCurrentUserId()
    TextInputFiled(label = "Name", placeholder = "Enter new name here", textState = userName, imeAction = ImeAction.Done, onAction = KeyboardActions {

        firebaseViewModel.getDocumentReference("users","userId",userId){result->
            firebaseViewModel.updateField("users",result.toString(),"userName",userName.value)
            keyboardController?.hide()

        }
    })
    TextInputFiled(label = "Email", placeholder = "Enter You email address here", textState = userEmail, imeAction = ImeAction.Done, onAction = KeyboardActions {
        firebaseViewModel.getDocumentReference("users","userId",userId){result->
            firebaseViewModel.updateField("users",result.toString(),"email",userEmail.value)
            keyboardController?.hide()
        }
    })
    TextInputFiled(label = "Mobile", placeholder = "Enter new number here", textState = userMobile, imeAction = ImeAction.Done, onAction = KeyboardActions {
        firebaseViewModel.getDocumentReference("users","userId",userId){result->
            firebaseViewModel.updateField("users",result.toString(),"phone",userMobile.value)
            keyboardController?.hide()
        }
    })

    SubmitButton(label = "Save", isValid =true ){
        onClick()
    }

}

