package com.dixitkumar.justreadit.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dixitkumar.justreadit.screens.search.SearchedItem

@Composable
fun UserAccountScreen(navController: NavController){
   UserAccountScreenUI()
}

@Composable
fun UserAccountScreenUI(){
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 80.dp)
        , color = Color.White) {
        Column (modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                Text(text = "Account",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp)

                getUserData()
            }
        }
    }
}

@Composable
fun getUserData(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(imageVector = Icons.Default.AccountCircle,
            contentDescription = "User image",
            modifier = Modifier.size(80.dp))

        Text(text = "Dixit",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black)

        Text(text = "dixitdk18deathawk@gmail.com",)

    }
}