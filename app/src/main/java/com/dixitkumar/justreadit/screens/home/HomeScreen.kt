package com.dixitkumar.justreadit.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dixitkumar.justreadit.Componenets.checkNetwork
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.model.MUser
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.search.SearchedItem
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.dixitkumar.justreadit.utils.getBooksFromIds
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.dixitkumar.justreadit.utils.getFont
import com.dixitkumar.justreadit.utils.getScreenHeight
import com.dixitkumar.justreadit.utils.getScreenWidth
import com.google.common.io.Files.append
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlin.math.abs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen (bottomNavController: NavController
                ,readerNavController: NavController,
                viewModel : HomeScreenViewModel= hiltViewModel(),
                fireBaseViewModel: FirebaseViewModel = hiltViewModel())
{
    val context = LocalContext.current
    val internetConnection = remember{ mutableStateOf(false) }


    if(checkNetwork(context)){
        internetConnection.value = true
    }else{
        internetConnection.value = false
    }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {if(internetConnection.value){
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

                    UserDetailRow(navController = readerNavController,fireBaseViewModel)
                    ReadingNowAreaUi(navController = readerNavController, homeScreenViewModel = viewModel, viewModel = fireBaseViewModel)
                    Spacer(modifier = Modifier.width(100.dp))
                    LikedGrid(homeViewModel = viewModel, fireBaseViewModel =fireBaseViewModel,navController = readerNavController)
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
        }else{
            Column (modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,){
                Image(painter = painterResource(id = R.drawable.no_connection),
                    contentDescription = "internet not Connected",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(400.dp)
                        .width(250.dp))

                Text(text = "Connect to the internet",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black)

                Text(text = "You're offline. Check your connection",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W300,
                    color = Color.Black)

                Button(onClick = {
                 internetConnection.value =  checkNetwork(context)
                 bottomNavController.navigate(ReaderScreens.HomeScreen.name){
                     popUpTo(0)
                 }
                },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue))
                ) {
                    Text(text = "Retry",
                        color = Color.White)
                }
            }
        }
    }
}
@Composable
fun UserDetailRow(
    navController: NavController,
    fireBaseViewModel: FirebaseViewModel
){
    val screenWidth = getScreenWidth()
    val halfWidth = screenWidth/50

    val user = GetFirebaseUserData(fireBaseViewModel)
    val userName = user?.userName
    val userIcon = user?.userIconUrl
    Column {
       Row (modifier = Modifier
           .fillMaxWidth()
           .padding(15.dp)
           ,verticalAlignment = Alignment.CenterVertically,
       ){
           Card(colors = CardDefaults.cardColors(Color.White),
               shape = CircleShape) {
               Image(painter = if(userIcon.isNullOrEmpty()) painterResource(id = R.drawable.user_icon) else rememberAsyncImagePainter(
                   model = userIcon
               ),
                   contentDescription = "user Icon",
                   modifier = Modifier
                       .size(45.dp)
                       .border(width = 1.dp, color = Color.DarkGray, shape = CircleShape),
                   contentScale = ContentScale.Fit)
           }
           Spacer(modifier = Modifier.width(10.dp))
           Text(text = buildAnnotatedString {
               withStyle(style = SpanStyle(fontWeight = FontWeight.W500, fontSize = 18.sp)){
                   append(if(userName.isNullOrEmpty()) "User" else userName)
               }
               withStyle(style = SpanStyle(fontWeight = FontWeight.W300, fontSize = 16.sp)){
                   append("\nReading Enthusiast")
               }
           })
       }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 1.5.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate(route = ReaderScreens.SearchScreen.name)
            }, colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Search here",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(1f))
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(38.dp)
                        .wrapContentWidth(Alignment.End))
            }
        }
}
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadingNowAreaUi(viewModel: FirebaseViewModel ,homeScreenViewModel: HomeScreenViewModel,navController: NavController) {
    val user = GetFirebaseUserData(viewModel = viewModel)
    if (!user?.readingList.isNullOrEmpty()) {
        val bookIdList = user?.readingList?.keys?.toList()
        val bookList: MutableList<Item>? =
            getBooksFromIds(booksIdsList = bookIdList!!, viewModel = homeScreenViewModel)
        if (!bookList.isNullOrEmpty() && bookList.size>=5) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Suggestions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
            Column(
                modifier = Modifier
                    .padding(4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                val screensWidth = getScreenWidth()
                val padding = (screensWidth/100)*20
                val pagerState = rememberPagerState(initialPage = 2, pageCount = { bookList.size})
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = padding),
                    verticalAlignment = Alignment.CenterVertically,
                    pageSpacing = 10.dp

                ) { page ->
                    val pageOffset = (pagerState.currentPage-page)+pagerState.currentPageOffsetFraction
                    val imagesize  = animateFloatAsState(targetValue =
                    if(pageOffset!=0.0f) 0.75f else 1f,
                        animationSpec = tween(durationMillis = 300)
                    )

                    Card (
                        modifier = Modifier
                            .width(220.dp)
                            .height(320.dp)
                            .clickable {
                                navController.navigate(route = ReaderScreens.DetailsScreen.name + "/${bookList[page].id}")
                            }
                            .graphicsLayer {
                                scaleX = imagesize.value
                                scaleY = imagesize.value
                            }
                            .offset {
                                val pageOffset =
                                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                IntOffset(
                                    x = (60.dp * pageOffset).roundToPx(),
                                    y = 0,
                                )
                            }
                            .padding(start = 5.dp, end = 5.dp, top = 5.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(12.dp)
                    ){
                     SuggestionBookItem(bookList[page])
                }
            }

            }
        }
    }
}

@Composable
fun SuggestionBookItem(item : Item?=null){
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        AsyncImage(
            model =ImageRequest.Builder(LocalContext.current)
                .data(item?.volumeInfo?.imageLinks?.thumbnail)
                .build(),
            contentDescription = "Sliding Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        )
    }
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
@Composable
fun LikedGrid(homeViewModel: HomeScreenViewModel,fireBaseViewModel: FirebaseViewModel ,navController: NavController){

val user = GetFirebaseUserData(viewModel =fireBaseViewModel)
val likedList = user?.likedBooks

var screensHeight = getScreenHeight(likedList)

if(likedList!=null && screensHeight !=null) {
    Log.d("Liked Books","liked List"+likedList.toString())
    val bookList: MutableList<Item>? =
        getBooksFromIds(booksIdsList = likedList, viewModel = homeViewModel)
    Log.d("Liked Books",bookList.toString())
    if (!bookList.isNullOrEmpty() ) {
        Column {
            Text(
                text = "You May Like These",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 5.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.height(screensHeight)
            ) {
                items(bookList) {
                    SearchedItem(book = it, navController = navController)
                }
            }
        }
    }
}
}

