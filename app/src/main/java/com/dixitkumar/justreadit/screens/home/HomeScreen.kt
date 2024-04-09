package com.dixitkumar.justreadit.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.dixitkumar.justreadit.utils.getScreenWidth
import com.google.common.io.Files.append
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlin.math.abs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (bottomNavController: NavController,readerNavController: NavController, viewModel : HomeScreenViewModel= hiltViewModel())
{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            if(viewModel.isLoading == true){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = colorResource(id = R.color.blue))
                }
            }else{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {

                    UserDetailRow(navController = readerNavController)
                    HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
                    ReadingNowAreaUi(navController = readerNavController)
                    Spacer(modifier = Modifier.width(100.dp))
                    BooksOptions(searchQuery = "Health", list =viewModel.life_style_books, readerNavController = readerNavController)
                    BooksOptions(searchQuery = "Motivation", list =viewModel.motivational_books, readerNavController = readerNavController)
                    BooksOptions(searchQuery = "Spirituality", list =viewModel.detective_novels, readerNavController = readerNavController)
                    BooksOptions(searchQuery = "History", list =viewModel.history_books, readerNavController = readerNavController)
                    BooksOptions(searchQuery = "Novels", list = viewModel.novels, readerNavController = readerNavController)
                    BooksOptions(searchQuery = "Computers ", list = viewModel.android_book, readerNavController = readerNavController)
                    BooksOptions(searchQuery = "Thriller", list =viewModel.mystery_thriller_books, readerNavController = readerNavController)
                    BooksOptions(readerNavController = readerNavController, searchQuery = "Manga", list =viewModel.manga )
                    BooksOptions(searchQuery = "Autobiography", list = viewModel.autobiography, readerNavController = readerNavController)
                }

            }
        }
}
@Composable
fun UserDetailRow(
    navController: NavController
){
    val screenWidth = getScreenWidth()
    val halfWidth = screenWidth/50
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
Row(Modifier.fillMaxWidth(0.9f), horizontalArrangement = Arrangement.Center){

    Text(text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)) {
            append("Boo")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
            )
        ) {
            append("ks")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = colorResource(id = R.color.blue)
            )
        ) {
            append("Z")
        }

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)) {
            val uni = String.format("U+1F50D")
            val code = uni.substring(2).toInt(16) // Remove the 'U+' prefix and convert to integer
            val char =  String(Character.toChars(code))
            append("one")
        }
    }
    )
}
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search Button",
                tint = Color.DarkGray,
                modifier = Modifier
                    .size(34.dp)
                    .clickable {
                        navController.navigate(route = ReaderScreens.SearchScreen.name)
                    }
            )

}
}
@Composable
fun ReadingNowAreaUi(viewModel: FirebaseViewModel = hiltViewModel(),homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),navController: NavController) {
    val user = GetFirebaseUserData(viewModel = viewModel)
    Log.d("TAG", user.toString())
    if (!user?.readingList.isNullOrEmpty()) {
        val bookIdList = user?.readingList
        val bookList :MutableList<Item>? = mutableListOf()
        if (bookIdList != null) {
            for (book in bookIdList) {
                val bookItem: Item? = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = homeScreenViewModel.getBookInfo(book)
                }.value.data
                if (bookItem != null) {
                    bookList?.add(bookItem)
                }
            }
        }
        if (!bookList.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reading Now ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(bookList) {
                        ReadingNowAreaItem(book = it) {
                            navController.navigate(route = ReaderScreens.DetailsScreen.name + "/${it.id}")
                        }
                    }
                }

            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = colorResource(id = R.color.blue))
            }
        }
    }
}

@Composable
fun ReadingNowAreaItem(book:Item,onClick: () -> Unit={}){
    Column {
        Spacer(modifier = Modifier.height(10.dp))

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
                .clickable {
                    onClick()
                },
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            elevation = CardDefaults.cardElevation(12.dp)
        ){
            Row(modifier = Modifier
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

                val bookImageUrl = book?.volumeInfo?.imageLinks?.thumbnail

                Image(
                    painter = if(bookImageUrl.isNullOrEmpty())
                        painterResource(id = R.drawable.no_img)else rememberAsyncImagePainter(
                        model = bookImageUrl
                    ),
                    contentDescription = "Book Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(170.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column {
                    Text(text ="Currently Reading", overflow = TextOverflow.Ellipsis,
                        color = Color.White, fontWeight = FontWeight.Bold,modifier = Modifier.padding(4.dp))
                    Text(text = book?.volumeInfo?.title.toString().split("(")[0], overflow = TextOverflow.Ellipsis,
                        color = Color.White)

                    Text(text = "Author : ${book?.volumeInfo?.authors.toString().split(",")[0]}",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,modifier = Modifier.padding(2.dp))

                    Text(text = "Date : ${book?.volumeInfo?.publishedDate.toString()}",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = Color.White,modifier = Modifier.padding(2.dp))

                }
            }
        }
    }
}

fun getBooks(mutableList: MutableList<String> = mutableListOf()):MutableList<String> {
    return mutableList
}

@Composable
fun BooksOptions(readerNavController: NavController,searchQuery : String,
                 list: List<Item>?) {
    if(list?.isNotEmpty() == true){
        SingleCategoryBooks(rowTitle = searchQuery, books =list, readerNavController = readerNavController ){
            readerNavController.navigate(route = ReaderScreens.MoreBookScreen.name+"/${"subject:"+searchQuery}")
        }
    }
}

@Composable
fun SingleCategoryBooks(readerNavController: NavController,rowTitle : String,
                        books : List<Item>?,
                        viewModel: HomeScreenViewModel = hiltViewModel(),onClick :()->Unit = {}){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${rowTitle} ",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )

            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                Row {
                    Icon(imageVector = Icons.Default.ArrowForward,
                        contentDescription = "view More Button",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onClick()
                            },
                        tint = Color.Black,)
                }
            }


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
    if(book == null){
        LinearProgressIndicator()
    }else{

    Column (modifier = Modifier
        .width(140.dp)
        .clickable {
            readerNavController.navigate(ReaderScreens.DetailsScreen.name + "/${book?.id}")
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Card (modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            colors =CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ){  val bookImageUrl = book?.volumeInfo?.imageLinks?.thumbnail
            Image(
                painter = if(bookImageUrl.isNullOrEmpty())
                    painterResource(id = R.drawable.no_img)else rememberAsyncImagePainter(
                    model = bookImageUrl
                ),
                contentDescription = "Book Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "${book?.volumeInfo?.title}",
            fontSize = 15.sp,
            maxLines = 1,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,modifier = Modifier.padding(start = 5.dp, end = 5.dp))
        val author  = book?.volumeInfo?.authors.toString().split(",")[0]
        Text(text = "${author}",
            fontSize = 15.sp,
            color = Color.DarkGray, maxLines = 1,
            overflow = TextOverflow.Ellipsis
            , modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
    }
}
}

