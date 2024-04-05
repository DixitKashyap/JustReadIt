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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.dixitkumar.justreadit.utils.getScreenWidth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope

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
                    ReadingNowArea()
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
fun UserDetailRow(navController: NavController){
    val screenWidthDp = getScreenWidth()
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(8.dp), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription ="Person Icon",
                tint = Color.Gray, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(10.dp))

        val username = remember { mutableStateOf("") }
        val userId = getCurrentUserId()

        LaunchedEffect(userId) {
            val userName = FirebaseFirestore.getInstance().collection("users")
                .get().addOnCompleteListener {
                    for (id in it.result){
                        if(userId.contentEquals(id["userId"].toString())){
                            username.value = id["userName"].toString()
                        }
                    }
                }
        }
        Column {
            Text(text = "Welcome,${username.value}",
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
        val spacer = (screenWidthDp/100)*20
        Spacer(modifier = Modifier.width(spacer))
        Icon(imageVector = Icons.Default.Search,
            contentDescription = "Search Button",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    navController.navigate(route = ReaderScreens.SearchScreen.name)
                }
        )
        Spacer(modifier = Modifier.width(15.dp))
        Icon(imageVector = Icons.Default.Logout,
            contentDescription ="Log Out Button",
            tint = Color.Gray,
            modifier = Modifier.clickable {
                FirebaseAuth.getInstance().signOut()
                Thread.sleep(1000)
                navController.navigate(route = ReaderScreens.LoginScreen.name)
            })

    }
}

@Composable
fun ReadingNowArea(viewModel: FirebaseViewModel = hiltViewModel(),homeScreenViewModel: HomeScreenViewModel = hiltViewModel()){
    var listUser = emptyList<MUser>()
    val currentUser = getCurrentUserId()

    if(!viewModel.data.value.data.isNullOrEmpty()){
        Log.d("TAG","USER ID ${currentUser.toString()}")
         listUser = viewModel.data.value?.data!!.toList().filter {
             it.userId.toString().contentEquals(currentUser)
         }
     if(listUser.isNotEmpty()){
     val user = listUser[0]

    if(!user.readingList.isNullOrEmpty()) {
        val bookListId = user.readingList?.get(0)!!
        val book = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
            value = homeScreenViewModel.getBookInfo(bookListId)
        }.value
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
                    AsyncImage(model = book?.data?.volumeInfo?.imageLinks?.smallThumbnail,
                        contentDescription ="Book Image"
                        , modifier = Modifier
                            .height(150.dp)
                            .width(100.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Text(text ="Currently Reading", overflow = TextOverflow.Ellipsis,
                            color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = book?.data?.volumeInfo?.title.toString(), overflow = TextOverflow.Ellipsis,
                            color = Color.White)

                        Text(text = "Author : ${book?.data?.volumeInfo?.authors.toString()}",
                            overflow = TextOverflow.Clip,
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            color = Color.White)

                        Text(text = "Date : ${book?.data?.volumeInfo?.publishedDate.toString()}",
                            overflow = TextOverflow.Clip,
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            color = Color.White)

                    }
                }
            }
        }
    }
 }

}else{
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = colorResource(id = R.color.blue))
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
            shape = RoundedCornerShape(20.dp)
        ){
            Image(painter = rememberAsyncImagePainter
                (model =book?.volumeInfo?.imageLinks?.thumbnail),
                contentDescription ="Book Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
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
}

