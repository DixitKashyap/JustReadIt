package com.dixitkumar.justreadit.screens.details

import android.os.DeadObjectException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.Componenets.RatingBar
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Comments
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.model.MBook
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseBookData
import com.google.rpc.context.AttributeContext

@Composable
fun AllCommentScreen (navController: NavController,bookId :String = "",
                      viewModel: DetailsScreenViewModel = hiltViewModel(),
                      firebaseViewModel: FirebaseViewModel = hiltViewModel()){
    val bookState = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
        value = viewModel.getBookInfo(bookId)
    }.value.data
    
    if(bookState!=null){
        Column(modifier = Modifier.fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(color = colorResource(id = R.color.blue))
        }
    }
    
    val book = GetFirebaseBookData(viewModel = firebaseViewModel,bookId = bookId)
    if(book!=null && bookState!=null){
        Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top){
                AllCommentScreenUi(navController,book=book,bookData = bookState)
            }
        }
    }
}

@Composable
fun AllCommentScreenUi(navController: NavController,book : MBook,bookData : Item? = null) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.Black,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
        )
    }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                BookIcon(bookImageUrl = bookData?.volumeInfo?.imageLinks?.thumbnail.toString())
            }
            item {
                Text(
                    text = bookData?.volumeInfo?.title.toString(),
                    fontSize = 27.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Black,
                    maxLines = 2,
                    lineHeight = 15.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                CommentButtonUi(str = "Reviews", imageUrl  = bookData?.volumeInfo?.imageLinks?.thumbnail.toString()){
                    navController.navigate(route =ReaderScreens.ReviewScreen.name+"/${bookData?.id}")
                }
            }
            if(book.comments!=null){
                items(book.comments){
                    CommentItem(it)
                }
            }else{
                item {
                    Text(text = "No Comments Here")
                }
            }
        }
    }


@Composable
fun CommentItem(commentItem: Comments? = null){
    if(commentItem!=null) {

        val rating = remember {
            mutableStateOf(commentItem?.rating)
        }
        Card(
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 15.dp,
                    bottom = 20.dp,
                    start = 10.dp,
                    end = 10.dp
                )
            ) {
                Row {
                    RatingBar(modifier = Modifier.weight(1f),
                        rating = rating.value!!,
                        enabled = false,
                        onPressRating = {})

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        tint = Color.Black,
                        contentDescription = "More Option",
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)
                    )
                }
                Text(
                    text = commentItem.commentTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = commentItem.commentContent,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "${commentItem.userName} ${commentItem.time}",
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}

