package com.dixitkumar.justreadit.screens.details

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.Componenets.RatingBar
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.home.SingleCategoryBooks
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseBookData
import com.dixitkumar.justreadit.utils.formatString
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.dixitkumar.justreadit.utils.getScreenWidth
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("SimpleDateFormat")
@Composable
fun Book_DetailsScreen(navController: NavController,bookId : String
                       ,viewModel: DetailsScreenViewModel = hiltViewModel(),
                       firebaseViewModel: FirebaseViewModel= hiltViewModel()
){

    val bookDetailsState = produceState<Resource<Item>>(initialValue =Resource.Loading() ) {
        value = viewModel.getBookInfo(bookId)
    }.value
    val firebaseBookData = GetFirebaseBookData(bookId = bookId, viewModel = firebaseViewModel)
    val currentUserId = getCurrentUserId()
    var bookRating = 0

    if(!firebaseBookData?.comments.isNullOrEmpty()){
        val userComments = firebaseBookData?.comments?.filter { it.userId == currentUserId }
        if(!userComments.isNullOrEmpty()){
            bookRating = userComments?.get(0)?.rating ?:0
        }
    }

    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = sdf.format(Date())
    val startedReadingStateText = remember{mutableStateOf("Started At...")}
    var startedReadingState = false
    val finishedReadingStateText = remember{ mutableStateOf("Finished At...") }
    var finishedReadingState = false


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
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.popBackStack()
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
            HorizontalDivider(thickness = 2.dp, color = Color.LightGray)

            Row (modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(270.dp)
                        .verticalScroll(rememberScrollState()),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    val bookImageUrl = bookDetailsState.data.volumeInfo?.imageLinks?.thumbnail

                    Image(
                        painter =  if(bookImageUrl.isNullOrEmpty())
                            painterResource(id = R.drawable.no_img)else rememberAsyncImagePainter(
                            model = bookImageUrl
                        ),
                        contentDescription = "Book Image",
                        modifier = Modifier
                            .width(180.dp)
                            .height(270.dp)
                    )
                }
            }
            //Book Title And Author
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = bookDetailsState.data.volumeInfo.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    maxLines = 3,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 34.sp,
                    modifier = Modifier.padding(start = 2.dp, top = 5.dp, bottom = 15.dp)
                )
                Text(
                    text = "by ${bookDetailsState.data.volumeInfo.authors}", fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                    maxLines = 2,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))
                val ratingVal = remember { mutableStateOf(bookRating) }
               
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RatingBar(
                        modifier = Modifier.weight(1f),
                        rating = ratingVal.value,
                        enabled = false
                    ) {
                        ratingVal.value = it
                    }
                    Spacer(modifier = Modifier.width((getScreenWidth() / 100) * 35))
                    Text(
                        text = "${bookDetailsState.data.volumeInfo.pageCount} pages",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.DarkGray, modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)
                    )
                }

                //Getting Starting Date From Firebase
                firebaseViewModel.getDocumentReference("users","userId", getCurrentUserId()){result->
                    firebaseViewModel.getFieldAsMap("users",result.toString(),"readingList"){
                        val currentBook = it[bookId]
                        if(currentBook.isNullOrEmpty()) {
                            startedReadingStateText.value = "Started At..."
                            startedReadingState = false
                        }else{
                            startedReadingStateText.value = "Started At ${currentBook}"
                            startedReadingState  = true
                        }
                    }
                }
                //Getting Finished Date From Firebase
                firebaseViewModel.getDocumentReference("users","userId", getCurrentUserId()){result->
                    firebaseViewModel.getFieldAsMap("users",result.toString(),"finishedReading"){
                        val currentBook = it[bookId]
                        if(currentBook.isNullOrEmpty()) {
                            finishedReadingStateText.value = "Finished At..."
                            finishedReadingState = false
                        }else{
                            finishedReadingStateText.value = "Finished At ${currentBook}"
                            finishedReadingState  = true
                        }
                    }
                }

                //Book Menu Area
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically){
                    bookMenu(bookDetailsState.data,viewModel = firebaseViewModel)
                }
                Row(
                    modifier = Modifier
                        .padding(start = 2.dp, top = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = startedReadingStateText.value,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(1f)
                            .clickable {
                                firebaseViewModel.getDocumentReference(
                                    "users",
                                    "userId",
                                    getCurrentUserId()
                                ) { result ->
                                    if (!startedReadingState) {
                                        firebaseViewModel.getFieldAsMap(
                                            "users",
                                            result.toString(),
                                            "readingList"
                                        ) {
                                            val contentList = it
                                            contentList.put(bookId, currentDate)
                                            if (contentList != null) {
                                                firebaseViewModel.updateField(
                                                    "users",
                                                    result.toString(),
                                                    "readingList",
                                                    contentList
                                                )
                                                startedReadingStateText.value =
                                                    "Started At " + currentDate
                                            }
                                        }
                                    } else {
                                        firebaseViewModel.getFieldAsMap(
                                            "users",
                                            result.toString(),
                                            "readingList"
                                        ) {
                                            val contentList = it
                                            contentList.remove(bookId)
                                            if (contentList != null) {
                                                firebaseViewModel.updateField(
                                                    "users",
                                                    result.toString(),
                                                    "readingList",
                                                    contentList
                                                )
                                                startedReadingStateText.value = "Started At..."
                                            }
                                        }
                                    }

                                }
                            }
                    )
                    Text(
                        text = finishedReadingStateText.value,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)
                            .clickable {
                                firebaseViewModel.getDocumentReference(
                                    "users",
                                    "userId",
                                    getCurrentUserId()
                                ) { result ->
                                    if (!finishedReadingState) {
                                        firebaseViewModel.getFieldAsMap(
                                            "users",
                                            result.toString(),
                                            "finishedReading"
                                        ) {
                                            val contentList = it
                                            contentList.put(bookId, currentDate)
                                            if (contentList != null) {
                                                firebaseViewModel.updateField(
                                                    "users",
                                                    result.toString(),
                                                    "finishedReading",
                                                    contentList
                                                )
                                                finishedReadingStateText.value =
                                                    "Finished At " + currentDate
                                            }
                                        }
                                    } else {
                                        firebaseViewModel.getFieldAsMap(
                                            "users",
                                            result.toString(),
                                            "finishedReading"
                                        ) {
                                            val contentList = it
                                            contentList.remove(bookId)
                                            if (contentList != null) {
                                                firebaseViewModel.updateField(
                                                    "users",
                                                    result.toString(),
                                                    "finishedReading",
                                                    contentList
                                                )
                                                finishedReadingStateText.value = "Finished At..."
                                            }
                                        }
                                    }

                                }
                            }
                    )
                }

            }

            //Book Description Area
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            bookDescription(book = bookDetailsState.data?.volumeInfo?.description.toString())
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            bookDetailsRow(bookDetailsState.data)
            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)

            val author_name = bookDetailsState.data?.volumeInfo?.authors?.get(0).toString()
            val bookCategory = bookDetailsState.data?.volumeInfo?.categories?.get(0).toString()
            val formattedName = author_name.slice(0..author_name.length-1)
            val formattedCategory = bookCategory.slice(0..bookCategory.length-1).split("/")[0]
            if(formattedName.isNotEmpty()){
                GetAuthorRelatedBooks(readerNavController = navController, searchQuery =formattedName,viewModel= viewModel)
            }

            if(!bookDetailsState.data.volumeInfo.title.isNullOrEmpty()){
                GetRelatedBooks(readerNavController = navController, searchQuery = bookDetailsState.data.volumeInfo.title,viewModel=viewModel)
            }
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            CommentButtonUi(str = "What Readers Says",imageUrl = bookDetailsState?.data?.volumeInfo?.imageLinks?.thumbnail.toString()){
                navController.navigate(ReaderScreens.ReviewScreen.name+"/${bookId}")
            }

            CommentArea(navController=navController,bookId=bookId)

        }
    }
    }
}


@Composable
 fun bookMenu(book : Item?, viewModel : FirebaseViewModel){
    var likeButtonIsClicked = remember { mutableStateOf(false) }
    val wishlistClicked = remember { mutableStateOf(false) }
    val context = LocalContext.current

    //Sharing The Content on Click of A Button
    val infointent = remember { Intent(Intent.ACTION_VIEW, book?.volumeInfo?.infoLink?.toUri()) }
    val shareIntent = remember{Intent(Intent.ACTION_SEND)}
    shareIntent.setType("text/plain")
    shareIntent.putExtra(Intent.EXTRA_TEXT,"${book?.volumeInfo?.title}\n Preview Link: ${book?.volumeInfo?.previewLink}")

    val userId = getCurrentUserId()
    //Liked Buttons
    val likedIcon = Icons.Default.ThumbUpOffAlt
    val likedIconClicked = Icons.Default.ThumbUp

    //Wishlist Button
    val wishlistButton = Icons.Outlined.BookmarkAdd
    val wishlistClickedButton = Icons.Filled.Bookmark

   //Updating The Like Button If The Open Book Has Already Been Liked
    viewModel.getDocumentReference("users","userId",userId){result->
        if (result != null) {
            viewModel.getFieldAsList("users",result,"likedBooks"){
               val bookList = it as MutableList<String>
                if(bookList?.contains(book?.id.toString()) == true){
                    likeButtonIsClicked.value = true
                }else{
                    likeButtonIsClicked.value = false
                }
            }
        }
    }


    //For Checking Whether The Book Exist Within the wishlist or not
    viewModel.getDocumentReference("users", "userId", userId) { result ->
        if (result != null) {
            viewModel.getFieldAsList("users", result, "wishlist") {
                val bookList = it as MutableList<String>
                if (bookList?.contains(book?.id.toString()) == true) {
                    wishlistClicked.value = true
                } else {
                    wishlistClicked.value = false
                }
            }
        }
    }

    val color = colorResource(id = R.color.blue)
    Row(modifier = Modifier
        .width(getScreenWidth() / 2)
        .padding(start = 0.dp, top = 12.dp, end = 0.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = if(!likeButtonIsClicked.value)
                likedIcon else likedIconClicked,
                contentDescription ="Like Icon",
                modifier = Modifier
                    .size(20.dp)
                    .weight(1f)
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
        Icon(imageVector =if(!wishlistClicked.value)
            wishlistButton else wishlistClickedButton,
            contentDescription ="Book Mark",
            modifier = Modifier
                .weight(1f)
                .size(20.dp)
                .clickable {
                    AddToDatabase(
                        viewModel = viewModel,
                        filedName = "wishlist",
                        book = book,
                        currentUserId = userId,
                        buttonState = wishlistClicked
                    )
                },
            tint =if(wishlistClicked.value)color else Color.Gray)
        Icon(imageVector = Icons.Default.Info,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(20.dp)
                .weight(1f)
                .clickable { context.startActivity(infointent) },
            tint = Color.Gray)

        Icon(imageVector = Icons.Default.Share,
            contentDescription ="Like Icon",
            modifier = Modifier
                .size(20.dp)
                .weight(1f)
                .clickable {
                    context.startActivity(shareIntent)
                },
            tint = Color.Gray)
    }

}

@Composable
fun GetRelatedBooks(readerNavController: NavController,searchQuery : String,
                          viewModel: DetailsScreenViewModel ) {
    LaunchedEffect(searchQuery) {
        if(viewModel.relatedBooks.isEmpty()){
            viewModel.getRelatedBook("${searchQuery}")
        }
    }
    if(viewModel.relatedBooks.isNotEmpty()){

        SingleCategoryBooks(rowTitle = "Suggested Books", books =viewModel.relatedBooks, readerNavController = readerNavController ){
            readerNavController.navigate(route = ReaderScreens.MoreBookScreen.name+"/${searchQuery}")
        }
    }
}

@Composable
fun GetAuthorRelatedBooks(readerNavController: NavController,searchQuery : String,
                 viewModel: DetailsScreenViewModel) {
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
    // Expanded Visibility State
    val expanded = remember{ mutableStateOf(false) }
    Column(modifier = Modifier.padding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 8.dp, top = 12.dp, bottom = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                text = "Description",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 19.sp,
                modifier = Modifier.weight(1f)
            )
            //Book Description expanded or not toggle button
            Image(imageVector = if(expanded.value)Icons.Filled.KeyboardArrowUp
            else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Book Description Expanded State",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(35.dp)
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .clickable {
                        expanded.value = !expanded.value
                    })
        }
        AnimatedVisibility(visible = expanded.value) {
            Column(modifier = Modifier.wrapContentHeight()){
                Text(
                    text = cleanDescription,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black, modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun bookDetailsRow(book : Item?){
    Column{
        val expanded = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 8.dp, top = 12.dp, bottom = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                text = "About The Book",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 19.sp,
                modifier = Modifier.weight(1f)
            )
            //Book Description expanded or not toggle button
            Image(imageVector = if(expanded.value)Icons.Filled.KeyboardArrowUp
            else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Book Description Expanded State",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(35.dp)
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .clickable {
                        expanded.value = !expanded.value
                    })
        }
        val bookCategory  =book?.volumeInfo?.categories.toString().trim()
        val formattedCategory = formatString(bookCategory,1,bookCategory.length-2,'/',',')

        val author = book?.volumeInfo?.authors.toString()
        val formattedAuthorName = formatString(author,1,author.length-2,'/',',')
        AnimatedVisibility(visible = expanded.value) {
            Column(modifier = Modifier.wrapContentHeight()){
                bookSpecificDetails(categories_label = "Categories", book =formattedCategory)
                bookSpecificDetails(categories_label = "Publisher", book =book?.volumeInfo?.publisher.toString(), width = 27.dp)
                bookSpecificDetails(categories_label = "Pages", book =book?.volumeInfo?.pageCount.toString() , width = 17.dp)
                bookSpecificDetails(categories_label = "Written By", book =formattedAuthorName, width = 40.dp)
                bookSpecificDetails(categories_label = "Release Date", book = book?.volumeInfo?.publishedDate.toString(), width = 55.dp)
            }
        }
    }
}
@Composable
fun bookSpecificDetails(categories_label : String,book :String,width : Dp = 20.dp) {
    Row(
        modifier = Modifier.padding(start = 12.dp, top = 8.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        if (book != null) {
            Text(
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black, modifier = Modifier.padding(8.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W600, fontSize = 17.sp)) {
                        append("${categories_label}")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp, color = Color.Black
                        )
                    ) {
                        append("\n${book}")
                    }
                })
        }
    }
}

    fun AddToDatabase(
        viewModel: FirebaseViewModel,
        book: Item?,
        filedName: String,
        currentUserId: String,
        buttonState: MutableState<Boolean>,
        onClick: () -> Unit = {},
    ) {
        var isProcessing: Boolean = false
        if (isProcessing) return

        isProcessing = true
        viewModel.getDocumentReference("users", "userId", currentUserId) { result ->
            if (result != null) {
                Log.d("TAG", result)
                if (!buttonState.value) {
                    viewModel.getFieldAsList("users", result, filedName) {
                        var contentList = it as MutableList<String>
                        contentList.add(book?.id.toString())

                        if (contentList != null) {
                            viewModel.updateField("users", result, filedName, contentList)
                            buttonState.value = true
                        }

                        isProcessing = true
                        onClick()
                    }

                } else {
                    viewModel.getFieldAsList("users", result, filedName) {
                        var contentList = it as MutableList<String>
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


@Composable
fun CommentArea(navController: NavController,bookId: String=""){
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
        Button(onClick = { navController.navigate(route = ReaderScreens.AllCommentScreen.name+"/${bookId}") },
            modifier = Modifier
                .padding(top = 20.dp, bottom = 30.dp)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
            Text(text = "See all reviews",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }
}
    @Composable
    fun CommentButtonUi(str : String,
        imageUrl : String,
        onClick: () -> Unit = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = str,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onClick()
                    }
                    .padding(12.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row {
                    Image(
                        painter = if (imageUrl.isNullOrEmpty())
                            painterResource(id = R.drawable.no_img)
                        else rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = "Book Image Url",
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color.White, shape = RoundedCornerShape(20.dp))
                    )
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Leave a Review",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 16.sp,
                        )
                        RatingBar(rating = 0, enabled = false) {
                        }
                    }

                }
            }
        }
    }

