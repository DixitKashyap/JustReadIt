package com.dixitkumar.justreadit.screens.account

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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

@Composable
fun AboutScreen(navController: NavController) {
    Surface (modifier = Modifier.fillMaxSize(), color = Color.White){
        TopBar(navController)
    }
}

@Composable
fun TopBar(navController: NavController?=null){
    Column {
        Row(modifier = Modifier.padding(20.dp)){
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Button",
                modifier = Modifier.clickable {
                    navController?.popBackStack()
                })
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "BookShelf",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start))
        }
        Content()

    }

}

@Composable
fun Content(){
    Column (modifier = Modifier.padding(10.dp)){
        Text(text ="Do you have an overflowing bookshelf and a never-ending \"to-be-read\" pile? [BookShelf] is your one-stop shop for organizing your reading life. ",
            fontSize = 19.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp))

        Text(text ="Create personalized shelves for the books you are \"Currently Reading\", \"Finished Books\", and even a \"Wishlist\" for future discoveries.",
            fontSize = 19.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp))

        Text(text ="Track your reading progress, see how many pages you've devoured, and celebrate those completed books with a sense of accomplishment.",
            fontSize = 19.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp))
        Text(text ="Never forget a book recommendation again! Add titles to your wishlist and keep them handy for your next library visit or bookstore adventure",
            fontSize = 19.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp))
    }
}
