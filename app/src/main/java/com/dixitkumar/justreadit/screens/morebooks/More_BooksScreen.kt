package com.dixitkumar.justreadit.screens.morebooks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.details.DetailsScreenViewModel
import com.dixitkumar.justreadit.screens.home.BookItems

@Composable
fun More_BooksScreen(navController: NavController,moreDetails : String,viewModel : DetailsScreenViewModel = hiltViewModel()) {

    var isLoading_author_books = viewModel.isLoading_author_books.value
    var isLoading_related_books = viewModel.isLoading_relatedBooks.value
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {


        LaunchedEffect(moreDetails) {
            if (moreDetails.contains("author") && viewModel.author_books.isEmpty()) {
                viewModel.getAuthorList(moreDetails)
            } else if (viewModel.relatedBooks.isEmpty()) {
                viewModel.getRelatedBook(moreDetails)
            }
        }
        Column {
            if(moreDetails.contains("author")){
                if(viewModel.isLoading){
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                       CircularProgressIndicator(color = colorResource(id = R.color.blue))
                    }
                }else{
                    MoreScreenUi(
                        moreDetails = "By ${moreDetails.split("=")[1]}",
                        list = viewModel.author_books,
                        navController = navController
                    )
                }
            }else{
                if(viewModel.isLoading){
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                       CircularProgressIndicator(color = colorResource(id = R.color.blue))
                    }
                }else{
                    MoreScreenUi(
                        moreDetails = if(moreDetails.contains("subject")) moreDetails.split(":")[1] else "Suggestion" ,
                            list =viewModel.relatedBooks,
                        navController=navController)
                }
            }

        }
    }
}

@Composable
fun MoreScreenUi(moreDetails: String,list: List<Item>,navController: NavController){
    Column (modifier = Modifier.padding(12.dp)){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.popBackStack()
                },
            tint = Color.Black)
        Icon(imageVector = Icons.Default.Search,
            contentDescription = "Back Button",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.navigate(route = ReaderScreens.SearchScreen.name)
                })

    }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){

            Text(text = "${moreDetails}",
                fontSize = 27.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
                )
        }
      LazyVerticalGrid(columns = GridCells.Adaptive(120.dp), content = {
          items(list){
               BookItems(book = it, readerNavController =navController)
          }
      })
}
}