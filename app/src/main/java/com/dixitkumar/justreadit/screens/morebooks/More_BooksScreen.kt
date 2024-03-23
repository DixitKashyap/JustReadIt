package com.dixitkumar.justreadit.screens.morebooks

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.screens.details.DetailsScreenViewModel
import com.dixitkumar.justreadit.screens.home.BookItems

@Composable
fun More_BooksScreen(navController: NavController,moreDetails : String,viewModel : DetailsScreenViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
//        if(viewModel.isLoading == true){
//            Row (modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
//                LinearProgressIndicator()
//            }
//        }else{
            Column {
                if(moreDetails.contains("author")){
                    viewModel.getAuthorList(moreDetails)
                    Log.d("TAG","More Content"+moreDetails)
                    if(viewModel.author_books.isNotEmpty()) {
                        MoreScreenUi(
                            moreDetails = "By ${moreDetails.split("=")[1]}",
                            list = viewModel.author_books,
                            navController = navController
                        )
                    }
                }else if(moreDetails.contains("subject")){
                    viewModel.getRelatedBook(moreDetails)
                    Log.d("TAG","More Content" +moreDetails)
                    if(viewModel.relatedBooks.isNotEmpty()){
                        MoreScreenUi(moreDetails = "${moreDetails.split(":")[1]}",
                            list =viewModel.relatedBooks,navController=navController)
                    }
                }
            }
        }
//    }
}

@Composable
fun MoreScreenUi(moreDetails: String,list: List<Item>,navController: NavController){
    Column (modifier = Modifier.padding(8.dp)){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            modifier = Modifier.size(30.dp),
            tint = Color.Black)
    }
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
      LazyVerticalGrid(columns = GridCells.Adaptive(100.dp), content = {
          items(list){
               BookItems(book = it, readerNavController =navController)
          }
      })
}
}