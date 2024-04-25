package com.dixitkumar.justreadit.screens.reading

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.home.HomeScreenViewModel
import com.dixitkumar.justreadit.screens.search.SearchedItem
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.dixitkumar.justreadit.utils.getBooksFromIds

@Composable
fun ReadingScreen(navController: NavController,firebaseViewModel: FirebaseViewModel= hiltViewModel(),homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {
    val user = GetFirebaseUserData(firebaseViewModel)
    val likedList = user?.likedBooks
    val startedReading = user?.readingList?.keys?.toList()
    val finishedReadingList = user?.finishedReading?.keys?.toList()
    val wishlist = user?.wishlist

    val likedBooksList = likedList
        ?.let { getBooksFromIds(booksIdsList = it, viewModel = homeScreenViewModel) }
    val startedReadingBooks = startedReading?.let {
        getBooksFromIds(
            booksIdsList = it,
            viewModel = homeScreenViewModel
        )?.toList()
    }
    val finishedReadingBooks =
        finishedReadingList?.let {
            getBooksFromIds(
                booksIdsList = it,
                viewModel = homeScreenViewModel
            )
        }
    val wishlistBooks =
        wishlist?.let { getBooksFromIds(booksIdsList = it, viewModel = homeScreenViewModel) }

    val selectedList = remember{ mutableStateOf(0) }
    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.White
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reading",
                    fontWeight = FontWeight.W500,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .weight(1f)
                        .wrapContentWidth(Alignment.End)
                        .clickable {
                            navController.navigate(route = ReaderScreens.SearchScreen.name)
                        }
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            val listOfButton = listOf(
                "Started",
                "Finished",
                "Liked",
                "Wishlist"
            )
            Column{
                TabRow(selectedTabIndex = selectedList.value,
                    containerColor = Color.White) {
                    listOfButton.forEachIndexed { index, s ->
                        Tab(selected =index == selectedList.value,
                            onClick = {
                                selectedList.value = index
                            },
                            text = { Text(text = s,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 16.sp)})
                    }
                }
               when(selectedList.value){
                   0 -> {
                       ReadingBookList(list = startedReadingBooks, navController =navController )
                   }
                   1 ->{
                       ReadingBookList(list = finishedReadingBooks, navController =navController )

                   }
                   2 ->{
                       ReadingBookList(list = likedBooksList, navController = navController)
                   }
                   3->{
                       ReadingBookList(list = wishlistBooks, navController =navController )
                   }
               }
            }
        }
    }
}

@Composable
fun ReadingBookList(list : List<Item>?,navController: NavController){
    val showProgress = remember {
        mutableStateOf(true)
    }
    Handler().postDelayed({
        showProgress.value = false
    },3000)
    LazyColumn {

            if (list != null) {
                items(list) {
                    SearchedItem(book = it, navController = navController)
                }
            }
            else {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                        if(showProgress.value){
                            CircularProgressIndicator(color = colorResource(id = R.color.blue))
                        }else{
                            Image(painter = painterResource(id = R.drawable.empty_state),
                                contentDescription ="no Item Found",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(300.dp))
                        }

                    }
                }
            }
        }
}

