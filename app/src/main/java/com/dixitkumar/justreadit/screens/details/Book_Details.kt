package com.dixitkumar.justreadit.screens.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item

@Composable
fun Book_DetailsScreen(navController: NavController,bookId : String
                       ,viewModel: DetailsScreenViewModel = hiltViewModel()
){

    val bookDetailsState = produceState<Resource<Item>>(initialValue =Resource.Loading() ) {
        value = viewModel.getBookInfo(bookId)
    }.value

    Text(text = "${bookDetailsState.data?.volumeInfo?.description}")

}