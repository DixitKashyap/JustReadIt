package com.dixitkumar.justreadit.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.Componenets.RatingBar
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Comments
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.screens.search.InputFiled
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.dixitkumar.justreadit.utils.getCurrentUserId
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun BookReviewScreen(navController: NavController,bookId : String,
                     viewModel: DetailsScreenViewModel = hiltViewModel(),
                     firebaseViewModel: FirebaseViewModel = hiltViewModel()){
    val currentUserId = getCurrentUserId()

    //Getting Book Data
    val book = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
        value = viewModel.getBookInfo(bookId)
    }.value
        BookReviewScreenUI(
            navController = navController,
            bookImageUrl = book.data?.volumeInfo?.imageLinks?.thumbnail.toString(),
            bookId,
            firebaseViewModel=firebaseViewModel
        )
}
@Composable
fun BookReviewScreenUI(navController: NavController?= null,
                       bookImageUrl: String ="",bookId: String,
                       firebaseViewModel: FirebaseViewModel){
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = sdf.format(Date())
    val currentUser = GetFirebaseUserData(viewModel = firebaseViewModel)
    val bookExist =  if(currentUser?.readingList?.containsKey(bookId) == true) true else false
    val currentUserName = currentUser?.userName
    val currentUserId = getCurrentUserId()
    val rating = remember{ mutableStateOf(0) }
    val reviewTitle = remember{ mutableStateOf("") }
    val reviewContent = remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column (Modifier.background(Color.White).fillMaxSize()){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.Black,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
        )
        Column (modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            BookIcon(bookImageUrl = bookImageUrl)

            Text(text = "Rate & review",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp,bottom = 20.dp))

            Text(text = "Tell Us your thoughts",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 20.dp))

            if(!bookExist){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .background(
                            Color.Red.copy(0.1f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ){
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "Review not avaliable",
                        tint = colorResource(id = R.color.dark_red),
                        modifier = Modifier
                            .size(45.dp)
                            .padding(8.dp)
                            .background(
                                color = Color.White, shape = CircleShape
                            )
                            .border(
                                width = 5.dp,
                                colorResource(id = R.color.dark_red),
                                shape = CircleShape
                            ))

                    Column (modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)){
                                append("Review not available\n")
                            }

                            withStyle(style = SpanStyle(fontWeight = FontWeight.W400, fontSize = 14.sp)){
                                append("You need to start reading before you can review the book ")
                            }
                        })
                    }
                }
            }

            Row (modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){

                Text(text = "Rating :",
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )

                RatingBar(rating = rating.value, enabled = true) {
                    rating.value = it
                }
            }

            Column {
                Text(text = "Title your review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Gray,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                )
               OutlinedTextField(
                   value =reviewTitle.value ,
                   onValueChange = {
                       reviewTitle.value = it
                   },
                   modifier = Modifier
                       .fillMaxWidth()
                       .wrapContentHeight()
                       .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                   placeholder = {
                       Text(text = "What Shoot out the most?",
                           fontSize = 16.sp,
                           fontWeight = FontWeight.Normal,
                           color = Color.Gray
                           )
                   },
                   shape = RoundedCornerShape(12.dp)
               )
            }
            Column {
                Text(text = "Write your review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Gray,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                )
                OutlinedTextField(
                    value =reviewContent.value ,
                    onValueChange = {
                        reviewContent.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                    placeholder = {
                        Text(text = "What did you like or dislike?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(12.dp)
                )
            }

            val isValid = reviewTitle.value.isNotEmpty() && reviewContent.value.isNotEmpty() && rating.value !=0 && bookExist==true
            val comments = Comments(
                userId = currentUserId,
                userName = currentUserName.toString(),
                commentTitle = reviewTitle.value,
                commentContent = reviewContent.value,
                time = currentDate,
                rating = rating.value
            )
            Button(onClick = {
              firebaseViewModel.getDocumentReference("books","bookId",bookId){
                  val result = it
                  if(result==null){
                      val commentsList = mutableListOf(comments)
                      firebaseViewModel.addBooksToCollection(bookId,bookComments = commentsList)
                      navController?.popBackStack()
                  }else{
                      firebaseViewModel.getFieldAsList("books",result.toString(),"comments"){
                        val commentList = it as MutableList<Comments>
                        commentList.add(comments)
                          firebaseViewModel.updateField("books",result.toString(),"comments",commentList)
                          navController?.popBackStack()
                      }
                  }
              }
            },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp), enabled = isValid) {
                Text(text = "Submit",
                    fontWeight = FontWeight.W500,
                    fontSize = 17.sp)
            }
        }
    }
}


@Composable
fun BookIcon(bookImageUrl : String){
    Card (elevation = CardDefaults.cardElevation(10.dp)){
        Image(
            painter =  if(bookImageUrl.isNullOrEmpty())
                painterResource(id = R.drawable.no_img) else rememberAsyncImagePainter(
                model = bookImageUrl
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "Book Image",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
    }
}