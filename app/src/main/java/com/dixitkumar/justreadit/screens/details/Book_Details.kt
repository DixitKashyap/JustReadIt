package com.dixitkumar.justreadit.screens.details

import android.util.Log
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.home.BookItems
import com.dixitkumar.justreadit.screens.home.BooksOptions
import com.dixitkumar.justreadit.screens.home.HomeScreenViewModel
import com.dixitkumar.justreadit.screens.home.SingleCategoryBooks
import com.dixitkumar.justreadit.screens.login.LoginButton
import com.google.android.play.integrity.internal.o

@Composable
fun Book_DetailsScreen(navController: NavController,bookId : String
                       ,viewModel: DetailsScreenViewModel = hiltViewModel()
){

    val bookDetailsState = produceState<Resource<Item>>(initialValue =Resource.Loading() ) {
        value = viewModel.getBookInfo(bookId)
    }.value

    if(bookDetailsState.data == null){
        Row {
            LinearProgressIndicator()
        }
    }else{

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        ) {
        Column(modifier = Modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start){
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(30.dp))
            }
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top){

                Card(modifier = Modifier
                    .width(150.dp)
                    .height(220.dp)
                    .verticalScroll(rememberScrollState()),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(Color.White)) {
                    AsyncImage(model = bookDetailsState.data?.volumeInfo?.imageLinks?.thumbnail,
                        contentDescription = "Book Thumbnail",
                        modifier = Modifier
                            .width(150.dp)
                            .height(220.dp))
                }
                Column (modifier = Modifier
                    .padding(12.dp)
                    .height(170.dp)){
                    Text(text = bookDetailsState.data?.volumeInfo?.title.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        maxLines = 3,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis)
                    Text(text = "by ${bookDetailsState.data?.volumeInfo?.authors}"
                    , fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        color = colorResource(id = R.color.blue),
                        overflow = TextOverflow.Ellipsis)

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Pages : ${bookDetailsState.data?.volumeInfo?.pageCount}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.blue).copy(0.5f), modifier = Modifier.padding(top = 6.dp))
                    Text(text = "Published : ${bookDetailsState.data?.volumeInfo?.publishedDate}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.blue).copy(0.5f), modifier = Modifier.padding(top = 6.dp))
                }

            }
            bottomRowArea()
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
            bookDetailsRow(bookDetailsState.data)
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
            bookDescription(book = bookDetailsState.data?.volumeInfo?.description.toString())
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)

            val author_name = bookDetailsState.data?.volumeInfo?.authors?.get(0).toString()
            val bookCategory = bookDetailsState.data?.volumeInfo?.categories?.get(0).toString()
            val formattedName = author_name.slice(0..author_name.length-1)
            val formattedCategory = bookCategory.slice(0..bookCategory.length-1).split("/")[0]
            if(formattedName.isNotEmpty()){
                GetAuthorRelatedBooks(readerNavController = navController, searchQuery = formattedName,viewModel= viewModel)
            }

            if(formattedCategory.isNotEmpty() && formattedCategory.contains("null") ==false){
                GetRelatedBooks(readerNavController = navController, searchQuery = formattedCategory,viewModel=viewModel)
            }
        }
    }
    }
}



@Composable
fun GetRelatedBooks(readerNavController: NavController,searchQuery : String,
                          viewModel: DetailsScreenViewModel = hiltViewModel()) {
    LaunchedEffect(searchQuery) {
        if(viewModel.relatedBooks.isEmpty()){
            viewModel.getRelatedBook("subject:${searchQuery}")
        }
    }
    if(viewModel.relatedBooks.isNotEmpty()){

        SingleCategoryBooks(rowTitle = searchQuery, books =viewModel.relatedBooks, readerNavController = readerNavController ){
            readerNavController.navigate(route = ReaderScreens.MoreBookScreen.name+"/${"subject:"+searchQuery}")
        }
    }
}

@Composable
fun GetAuthorRelatedBooks(readerNavController: NavController,searchQuery : String,
                 viewModel: DetailsScreenViewModel = hiltViewModel()) {
    LaunchedEffect(searchQuery) {
        if(viewModel.author_books.isEmpty()){
            viewModel.getAuthorList("author=${searchQuery}")

        }
    }
    if(viewModel.author_books.isNotEmpty()){

        SingleCategoryBooks(rowTitle = searchQuery, books =viewModel.author_books, readerNavController = readerNavController ){
            readerNavController.navigate(route = ReaderScreens.MoreBookScreen.name+"/${"author="+searchQuery}")
        }
    }
}

@Composable
fun bookDescription(book : String){
    val cleanDescription = HtmlCompat.fromHtml(book,HtmlCompat.FROM_HTML_MODE_LEGACY).toString()


    Column(modifier = Modifier.padding(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {


            Text(
                text = "Description",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
            Text(
                text = "${cleanDescription}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black.copy(0.5f), modifier = Modifier.padding(top = 6.dp)
            )
    }
}

@Composable
fun bookDetailsRow(book : Item?){
    Column{
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Text(text = "MORE INFO",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize =20.sp)
        }
        bookSpecificDetails(categories_label = "Category", book =book?.volumeInfo?.categories.toString(), width = 30.dp)
        bookSpecificDetails(categories_label = "Publisher", book =book?.volumeInfo?.publisher.toString(), width = 27.dp)
        bookSpecificDetails(categories_label = "Page Count", book =book?.volumeInfo?.pageCount.toString() , width = 17.dp)
        bookSpecificDetails(categories_label = "Author", book =book?.volumeInfo?.authors.toString(), width = 40.dp)
        bookSpecificDetails(categories_label = "Date", book = book?.volumeInfo?.publishedDate.toString(), width = 55.dp)

    }

}
@Composable
fun bookSpecificDetails(categories_label : String,book :String,width : Dp = 20.dp){
    Row(modifier = Modifier.padding(start = 12.dp, top = 8.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment =  Alignment.Top){
        Text(text = categories_label,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize =15.sp)
        Spacer(modifier = Modifier.width(width))
        if (book != null) {
            Text(text = "${book}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black.copy(0.5f), modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
fun bottomRowArea(){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){

        LoginButton(modifier = Modifier
            .width(155.dp)
            .height(50.dp),
            label = "Add To Wishlist",
            cornerRadius = 20.dp,
            color = colorResource(id = R.color.blue),
            elevation = 5.dp)

        LoginButton(modifier = Modifier
            .width(155.dp)
            .height(50.dp),
            label = "Start Reading",
            cornerRadius = 20.dp,
            color = Color.LightGray,
            elevation = 5.dp)
    }

}
