package com.dixitkumar.justreadit.screens.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.screens.search.SearchScreenViewModel
import com.dixitkumar.justreadit.screens.search.SearchedItem

@Composable
fun WishlistScreen(navController: NavController){
   WishlistScreenUi(navController)
}

@Composable
fun WishlistScreenUi(navController: NavController,viewModel: SearchScreenViewModel = hiltViewModel()){
    val bookstate = viewModel.default_list
    Surface(modifier = Modifier.fillMaxSize()
        .padding(bottom = 80.dp)
        , color = Color.White) {
        Column (modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier.fillMaxWidth().padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically){
                Text(text = "Wishlist",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp)
            }

            LazyColumn {
                items(bookstate){
                    SearchedItem(navController = navController,
                        book = it)
                }
            }
        }
    }

}