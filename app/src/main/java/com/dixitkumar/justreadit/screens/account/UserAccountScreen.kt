package com.dixitkumar.justreadit.screens.account

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.google.firebase.auth.FirebaseAuth
import java.io.File


@Composable
fun UserAccountScreen(navController: NavController,firebaseViewModel: FirebaseViewModel= hiltViewModel()){
    val user = GetFirebaseUserData(viewModel = firebaseViewModel)
   UserAccountScreenUI(navController,user = user)
}

@Composable
fun UserAccountScreenUI(navController: NavController,user:MUser?){
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 80.dp)
        , color = Color.White) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()){
            TopRow()
            UserData(user = user){
                navController.navigate(route = ReaderScreens.EditAccountScreens.name)
            }
            AccountOption(navController)
            Spacer(modifier = Modifier.height(100.dp))
            OtherOption(){
                FirebaseAuth.getInstance().signOut()
                navController.navigate(ReaderScreens.LoginScreen.name) {
                   popUpTo(0)
                }
            }
        }
    }
}

@Composable
fun TopRow(){
    Row(modifier = Modifier.padding(20.dp),
        horizontalArrangement = Arrangement.Start){
        Text(text = "My Account",
            fontSize = 19.sp,
            fontWeight = FontWeight.W500)
    }
}


@Composable
fun UserData(user: MUser?,onClick: () -> Unit){
  
    val userName = user?.userName
    val userEmail = user?.email
    val userIcon = user?.userIconUrl
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = CircleShape,
            modifier = Modifier.size(100.dp)){
            Image(
                painter = if(userIcon.isNullOrEmpty()) painterResource(id = R.drawable.user_icon) else rememberAsyncImagePainter(
                    model = userIcon
                ),
                contentDescription = "User Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }


        Text(text = if(userName.isNullOrEmpty()) "User" else userName,
            fontSize = 22.sp,
            fontWeight = FontWeight.W300,
            maxLines = 1,
            color = Color.Black)

        Row(modifier = Modifier.padding(10.dp)){
            Icon(imageVector = Icons.Outlined.Email,
                contentDescription ="Email Icons",
                tint = Color.Gray)

            Text(text = if(userEmail.isNullOrEmpty()) "Email" else userEmail,
                fontSize = 19.sp,
                fontWeight = FontWeight.W300,
                maxLines = 1,
                color = Color.Gray)
        }

        Button(onClick = {
                         onClick()
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            , border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Text(text = "Edit Profile",
                color = colorResource(id = R.color.blue))
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray,
            modifier = Modifier.padding(20.dp))
    }
}


@Composable
fun AccountOption(navController: NavController){
    Column (modifier = Modifier.padding(20.dp)){
        Text(text = "Account Options",
            fontSize = 15.sp,
            color = Color.Gray,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 20.dp))

        OptionsRow(label = "About BookShelf"){
            navController.navigate(route = ReaderScreens.AboutTheAppScreen.name)
        }

}
}

@Composable
fun OptionsRow(label :String,onClick: () -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick()
        }
        .padding(top = 10.dp, bottom = 10.dp)){
        Text(text = label,
            fontSize = 17.sp,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f))

        Icon(imageVector = Icons.Outlined.ArrowForwardIos ,
            contentDescription = "More Info",
            tint = Color.DarkGray)
    }
}

@Composable
fun OtherOption(onClick:()->Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    , verticalAlignment = Alignment.CenterVertically){
        Column (verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Sign Out",
                fontSize = 17.sp,
                color = colorResource(id = R.color.blue),
                modifier = Modifier.clickable {
                    onClick()
                })

            Text(text = "BookShelf v1.0",
                color = Color.Gray,
                modifier = Modifier.padding(top = 20.dp))
        }
    }
}