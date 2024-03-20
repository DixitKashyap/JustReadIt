package com.dixitkumar.justreadit.screens.home

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (bottomNavController: NavController,readerNavController: NavController, viewModel : HomeScreenViewModel= hiltViewModel())
{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                UserDetailRow()
                TopRow()
                ReadingNowArea()
                Spacer(modifier = Modifier.width(100.dp))
                BooksOptions(searchQuery = "Lifestyle", list =viewModel.life_style_books, readerNavController = readerNavController)
                BooksOptions(searchQuery = "Motivation", list =viewModel.motivational_books, readerNavController = readerNavController)
                BooksOptions(searchQuery = "Fictional", list =viewModel.fictional_books, readerNavController = readerNavController)
                BooksOptions(searchQuery = "History", list =viewModel.history_books, readerNavController = readerNavController)
                BooksOptions(searchQuery = "Comics", list = viewModel.comic_books, readerNavController = readerNavController)
                BooksOptions(searchQuery = "Android ", list = viewModel.android_book, readerNavController = readerNavController)
                BooksOptions(searchQuery = "Finance", list =viewModel.finance_books, readerNavController = readerNavController)
            }
        }
}
@Composable
fun UserDetailRow(){
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(8.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription ="Person Icon",
                tint = Color.Gray, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = "Welcome,Dixit",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
                )
            Text(text = "Aspiring Android Developer",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(130.dp))
        Icon(imageVector = Icons.Default.Logout, contentDescription ="Log Out Button", tint = Color.Gray)
    }
}
@Composable
fun TopRow(){
    Card (modifier = Modifier.padding(top = 8.dp, start = 14.dp, end = 14.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(30.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Type book name or author",
                fontSize = 17.sp,
                color = Color.Gray)
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.LightGray)
        }
    }
}

@Composable
fun ReadingNowArea(viewModel: HomeScreenViewModel = hiltViewModel()){
    val listOfBooks = viewModel.default_list
    var book : Item? =null
    if (listOfBooks.isNotEmpty()) {
        book = listOfBooks[0]
    }
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Reading Now ",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "View All >",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            elevation = CardDefaults.cardElevation(12.dp)
        ){
            Row(modifier = Modifier
                .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                AsyncImage(model = book?.volumeInfo?.imageLinks?.smallThumbnail,
                    contentDescription ="Book Image"
                    , modifier = Modifier
                        .height(150.dp)
                        .width(100.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(text ="Currently Reading", overflow = TextOverflow.Ellipsis,
                        color = Color.White, fontWeight = FontWeight.Bold)
                    Text(text = book?.volumeInfo?.title.toString(), overflow = TextOverflow.Ellipsis,
                        color = Color.White)

                    Text(text = "Author : ${book?.volumeInfo?.authors.toString()}",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = Color.White)

                    Text(text = "Date : ${book?.volumeInfo?.publishedDate.toString()}",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = Color.White)

                }
            }
        }
    }
}

@Composable
fun BooksOptions(readerNavController: NavController,searchQuery : String,
                 list: List<Item>?) {
    if(list?.isNotEmpty() == true){
        SingleCategoryBooks(rowTitle = searchQuery, books =list, readerNavController = readerNavController )
    }
}

@Composable
fun SingleCategoryBooks(readerNavController: NavController,rowTitle : String,
                        books : List<Item>?,
                        viewModel: HomeScreenViewModel = hiltViewModel()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${rowTitle} ",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "View All >",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Row(modifier = Modifier.padding(1.dp)) {
          LazyRow {
              if(books!=null) {
                  items(books) {
                      BookItems(book = it,readerNavController)
                  }
              }
          }
        }
}

@Composable
fun BookItems(book : Item?,readerNavController: NavController){
    Column (modifier = Modifier
        .width(140.dp)
        .clickable {
                   readerNavController.navigate(ReaderScreens.DetailsScreen.name+"/${book?.id}")
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Card (
            colors =CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(20.dp)
        ){
            Image(painter = rememberAsyncImagePainter
                (model =book?.volumeInfo?.imageLinks?.thumbnail),
                contentDescription ="Book Image",
                modifier = Modifier
                    .width(120.dp)
                    .height(170.dp)
               )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "${book?.volumeInfo?.title}",
            fontSize = 15.sp,
            maxLines = 2,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis)
        val author  = book?.volumeInfo?.authors.toString().split(",")[0]
        Text(text = "${author}",
            fontSize = 15.sp,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis
        )
    }
}

