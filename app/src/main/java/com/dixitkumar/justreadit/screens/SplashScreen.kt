package com.dixitkumar.justreadit.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
  SplashScreenUi(navController)
}

@Composable
fun SplashScreenUi(navController: NavController){

    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.9f,
            tween(durationMillis = 1500,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }))
        delay(1000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.popBackStack()
            navController.navigate(route = ReaderScreens.LoginScreen.name)
        }else{
            navController.popBackStack()
            navController.navigate(route = ReaderScreens.MainScreen.name)
        }

    }
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(15.dp)
            .size(440.dp)
        , shape = CircleShape,
        color = Color.White
    ){
        Column(modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.splash_image),
                modifier = Modifier
                    .width(300.dp)
                    .height(250.dp),
                contentDescription = "Splash Screen Icons",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
                )
            ReaderLogo()
            Spacer(modifier = Modifier.height(200.dp))
            CircularProgressIndicator(color = colorResource(id = R.color.blue))

        }
    }
}

@Composable
fun ReaderLogo() {
    Text(modifier = Modifier.padding(15.dp),text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.Black)) {
            append("Book")
        }
      
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = colorResource(id = R.color.blue)
            )
        ) {
            append("S")
        }

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.Black)) {
            append("helf")
        }
    }
    )
}

