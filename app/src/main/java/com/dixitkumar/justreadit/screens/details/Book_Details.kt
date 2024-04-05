package com.dixitkumar.justreadit.screens.details

import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbDownOffAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.home.SingleCategoryBooks
import com.dixitkumar.justreadit.screens.login.LoginButton
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.formatString
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.google.android.play.integrity.internal.i

@Composable
fun Book_DetailsScreen(navController: NavController,bookId : String
                       ,viewModel: DetailsScreenViewModel = hiltViewModel()
){

    val bookDetailsState = produceState<Resource<Item>>(initialValue =Resource.Loading() ) {
        value = viewModel.getBookInfo(bookId)
    }.value

    if(bookDetailsState.data == null){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = colorResource(id = R.color.blue))
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
                horizontalArrangement = Arrangement.SpaceBetween){
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            if (navController.previousBackStackEntry?.destination?.route.contentEquals(
                                    ReaderScreens.MainScreen.name
                                )
                            ) {
                                navController.navigate(navController.previousBackStackEntry?.destination?.route.toString()) {
                                    popUpTo(navController.previousBackStackEntry?.destination?.route.toString(),) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navController.popBackStack()
                            }
                        })

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
            val userId  = getCurrentUserId()
            val firebaseViewModel : FirebaseViewModel = hiltViewModel()
            bottomRowArea(viewModel = firebaseViewModel,book = bookDetailsState.data,userId =userId)
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp)
            bookMenu(bookDetailsState.data,viewModel = firebaseViewModel)
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

    //For Handling Back press Using Device
    BackHandler {
        if (navController.previousBackStackEntry?.destination?.route.contentEquals(
                ReaderScreens.MainScreen.name
            )
        ) {
            navController.navigate(navController.previousBackStackEntry?.destination?.route.toString()){
                popUpTo(navController.previousBackStackEntry?.destination?.route.toString(),){
                    inclusive = true
                }
            }
        } else {
            navController.popBackStack()
        }
    }

}


@Composable
 fun bookMenu(book : Item?, viewModel : FirebaseViewModel){
    var likeButtonIsClicked = remember { mutableStateOf(false) }
    var dislikeButtonIsClicked = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val infointent = remember { Intent(Intent.ACTION_VIEW, book?.volumeInfo?.infoLink?.toUri()) }
    val previewintent = remember {Intent(Intent.ACTION_VIEW,book?.volumeInfo?.previewLink?.toUri())}
    val shareIntent = remember{Intent(Intent.ACTION_SEND)}
    shareIntent.setType("text/plain")
    shareIntent.putExtra(Intent.EXTRA_TEXT,"${book?.volumeInfo?.title}\n Preview Link: ${book?.volumeInfo?.previewLink}")

    val userId = getCurrentUserId()
    val likedIcon = Icons.Default.ThumbUpOffAlt
    val likedIconClicked = Icons.Default.ThumbUp
    val dislikedIcon = Icons.Default.ThumbDownOffAlt
    val dislikedIconClicked = Icons.Default.ThumbDown

    viewModel.getDocumentReference("users","userId",userId){result->
        if (result != null) {
            viewModel.getField("users",result,"likedBooks"){
               val bookList = it
                if(bookList?.contains(book?.id.toString()) == true){
                    likeButtonIsClicked.value = true
                }else{
                    likeButtonIsClicked.value = false
                }
            }
        }
    }

    val color = colorResource(id = R.color.blue)
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = if(!likeButtonIsClicked.value) likedIcon else likedIconClicked,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    AddToDatabase(
                        viewModel = viewModel,
                        book = book,
                        filedName = "likedBooks",
                        currentUserId = userId,
                        buttonState = likeButtonIsClicked
                    )
                },
            tint = if(likeButtonIsClicked.value)color else Color.Gray)
        Icon(imageVector = if(!dislikeButtonIsClicked.value)dislikedIcon else dislikedIconClicked,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    if (!dislikeButtonIsClicked.value) {
                        dislikeButtonIsClicked.value = true
                    } else {
                        dislikeButtonIsClicked.value = false
                    }
                },
            tint = if(dislikeButtonIsClicked.value) color else Color.Gray)
        Icon(imageVector = Icons.Default.Info,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable { context.startActivity(infointent) },
            tint = Color.Gray)
        Icon(imageVector = Icons.Default.Preview,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable { context.startActivity(previewintent) },
            tint = Color.Gray)
        Icon(imageVector = Icons.Default.Share,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    context.startActivity(shareIntent)
                },
            tint = Color.Gray)
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
        val bookCategory  =book?.volumeInfo?.categories.toString().trim()
        val formattedCategory = formatString(bookCategory,1,bookCategory.length-2,'/',',')

        val author = book?.volumeInfo?.authors.toString()
        val formattedAuthorName = formatString(author,1,author.length-2,'/',',')
        bookSpecificDetails(categories_label = "Category", book =formattedCategory, width = 30.dp)
        bookSpecificDetails(categories_label = "Publisher", book =book?.volumeInfo?.publisher.toString(), width = 27.dp)
        bookSpecificDetails(categories_label = "Page Count", book =book?.volumeInfo?.pageCount.toString() , width = 17.dp)
        bookSpecificDetails(categories_label = "Author", book =formattedAuthorName, width = 40.dp)
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
fun bottomRowArea(viewModel: FirebaseViewModel,book: Item?,userId : String){
    val wishlistClicked = remember{ mutableStateOf(false) }
    val wishListText = remember { mutableStateOf("Add to Wishlist") }
    val startReadingClicked =  remember{ mutableStateOf(false) }
    val readingListText = remember { mutableStateOf("Start Reading") }


    //For Checking Whether The Book Exist Within the wishlist or not
    viewModel.getDocumentReference("users","userId",userId){result->
        if (result != null) {
            viewModel.getField("users",result,"wishlist"){
                val bookList = it
                if(bookList?.contains(book?.id.toString()) == true){
                    wishlistClicked.value = true
                    wishListText.value = "Wishlisted"
                }else{
                    wishlistClicked.value = false
                    wishListText.value = "Add to Wishlist"
                }
            }
        }
    }
    //For Checking Whether The Book Exist Within the ReadingList or not
    viewModel.getDocumentReference("users","userId",userId){result->
        if (result != null) {
            viewModel.getField("users",result,"readingList"){
                val bookList = it
                if(bookList?.contains(book?.id.toString()) == true){
                    startReadingClicked.value = true
                    readingListText.value = "Reading..."
                }else{
                    startReadingClicked.value = false
                    readingListText.value = "Start Reading"
                }
            }
        }
    }


    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){

        LoginButton(modifier = Modifier
            .width(155.dp)
            .height(50.dp),
            label = wishListText,
            cornerRadius = 20.dp,
            color = colorResource(id = R.color.blue),
            elevation = 5.dp,
            onClick = {
                AddToDatabase(viewModel = viewModel, filedName = "wishlist",book = book, currentUserId = userId, buttonState = wishlistClicked)
                if(!wishlistClicked.value){
                    wishListText.value = "Wishlisted"
                }else{
                    wishListText.value = "Add To Wishlist"
                }
            })

        LoginButton(modifier = Modifier
            .width(155.dp)
            .height(50.dp),
            onClick = {
                AddToDatabase(viewModel = viewModel, filedName = "readingList",book = book, currentUserId = userId, buttonState = startReadingClicked)
                if(!startReadingClicked.value){
                    readingListText.value = "Reading..."
                }else{
                    readingListText.value = "Start Reading"
                }
            },
            label =readingListText,
            cornerRadius = 20.dp,
            color = colorResource(id = R.color.blue),
            elevation = 5.dp)
    }
}


fun AddToDatabase(viewModel : FirebaseViewModel, book: Item?,filedName : String, currentUserId: String, buttonState : MutableState<Boolean>,onClick:()->Unit={}){
    var isProcessing : Boolean = false
    if(isProcessing) return

    isProcessing = true
    viewModel.getDocumentReference("users","userId",currentUserId){result->
        if (result != null) {
            Log.d("TAG",result)
                if(!buttonState.value){
                    viewModel.getField("users",result,filedName) {
                        var contentList = it
                        contentList?.add(book?.id.toString())

                        if (contentList != null) {
                            viewModel.updateField("users", result, filedName, contentList)
                            buttonState.value = true
                        }

                        isProcessing = true
                        onClick()
                    }
                }else {
                    viewModel.getField("users", result, filedName) {
                        var contentList = it
                        contentList?.removeAll(listOf(book?.id))

                        if (contentList != null) {
                            viewModel.updateField("users", result, filedName, contentList)
                            buttonState.value = false
                        }
                        isProcessing = true
                        onClick()
                    }
                }
        }
    }
}


