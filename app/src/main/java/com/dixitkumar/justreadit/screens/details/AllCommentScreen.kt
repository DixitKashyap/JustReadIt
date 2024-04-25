package com.dixitkumar.justreadit.screens.details

import android.annotation.SuppressLint
import android.os.DeadObjectException
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dixitkumar.justreadit.Componenets.CustomAlertDialog
import com.dixitkumar.justreadit.Componenets.RatingBar
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Comments
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.model.MBook
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseBookData
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.google.rpc.context.AttributeContext

@Composable
fun AllCommentScreen (navController: NavController,bookId :String = "",
                      viewModel: DetailsScreenViewModel = hiltViewModel(),
                      firebaseViewModel: FirebaseViewModel = hiltViewModel()){
    val bookState = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
        value = viewModel.getBookInfo(bookId)
    }.value.data

    val book = GetFirebaseBookData(viewModel = firebaseViewModel,bookId = bookId)
        Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top){
                AllCommentScreenUi(navController,book=book,bookData = bookState,viewModel=firebaseViewModel,bookId)
            }
        }
}

@SuppressLint("RestrictedApi")
@Composable
fun AllCommentScreenUi(navController: NavController,book : MBook?,bookData : Item? = null,viewModel: FirebaseViewModel,bookId : String) {

    var bookCommentList : MutableList<Comments>? = mutableListOf()
    if(book?.comments!=null){
        bookCommentList = book.comments as MutableList<Comments>?
    }
    val currentUserId = getCurrentUserId()

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                Column (modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Text(
                        text = bookData?.volumeInfo?.title.toString(),
                        fontSize = 27.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                        maxLines = 2,
                        lineHeight = 34.sp,
                        modifier = Modifier
                            .padding(25.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                    Text(text = "All Review",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                        maxLines = 2,
                        lineHeight = 34.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally))
                }
            }

            //Showing all the comment for the book
            if(bookCommentList!=null ){
                itemsIndexed(bookCommentList){index,bookItem->
                    val clickable = if(currentUserId.contentEquals(bookItem.userId)) true else false
                    CommentItem(bookItem,clickable){

//                        //If user click on the delete button then this will delete the comment and refresh the screen
                        viewModel.getDocumentReference("books",
                            "bookId",bookId
                        ){result->
                           val commentList = bookCommentList
                            if(commentList[index].userId == currentUserId){
                                commentList.removeAt(index)
                                viewModel.updateField(
                                    "books",result.toString(),
                                    "comments",commentList)

                                navController.navigate(route = ReaderScreens.AllCommentScreen.name+"/${bookId}"){
                                    popUpTo(route = ReaderScreens.AllCommentScreen.name+"/${bookId}"){
                                        inclusive = true
                                    }
                                }
                            }
                        }


                        Log.d("TAG","Delete Comment Clicked")
                    }
                }
            }
            if(bookCommentList.isNullOrEmpty()){
                item {
                    Text(text = "No Comments Avaliable for this book,now",
                        modifier = Modifier.padding(20.dp))
                }
            }
        }
    }


@Composable
fun CommentItem(commentItem: Comments? = null,clickable: Boolean,onClick: () -> Unit={}){
    val shouldShowDialog  = remember { mutableStateOf(false) }

    if(commentItem!=null) {

        val rating = remember {
            mutableStateOf(commentItem.rating)
        }
        Card(
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)) {
                Row {
                    RatingBar(modifier = Modifier.weight(1f),
                        rating = rating.value!!,
                        enabled = false,
                        onPressRating = {})

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        tint = if(!clickable)Color.Gray else Color.Black,
                        contentDescription = "More Option",
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)
                            .clickable {
                                if(clickable){
                                    shouldShowDialog.value = !shouldShowDialog.value
                                }
                            }
                    )
                }


                if(shouldShowDialog.value){
                    CustomAlertDialog(
                        onDismiss = {
                            shouldShowDialog.value = false
                        },
                        onExit = {
                            onClick()
                        })
                }else{
                    Box{

                    }
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
                    modifier = Modifier.padding(start = 8.dp,end = 8.dp, top = 2.dp)
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

