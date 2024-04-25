package com.dixitkumar.justreadit.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.dixitkumar.justreadit.navigation.ReaderScreens
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

    //Getting Book Data
    val book = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
        value = viewModel.getBookInfo(bookId)
    }.value
    Surface(modifier = Modifier.fillMaxWidth(),
        color = Color.White) {
        BookReviewScreenUI(
            navController = navController,
            bookImageUrl = book.data?.volumeInfo?.imageLinks?.thumbnail.toString(),
            bookId,
            firebaseViewModel=firebaseViewModel
        )
    }

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
    Column (
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())){
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.Black,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .clickable {
                    navController?.popBackStack()
                }
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

            BackHandler {
                navController?.popBackStack()
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
            //"Title your review"
            //What Shoot out the most?
            val keyboardController = LocalSoftwareKeyboardController.current
            TextInputFiled(label = "Title your review", placeholder = "What Shoot out the most?",textState = reviewTitle,
                imeAction = ImeAction.Done, onAction = KeyboardActions {
                keyboardController?.hide()
            })
            //"Write your review"
            //What did you like or dislike?
            TextInputFiled(label = "Write your review", placeholder ="What did you like or dislike?" , textState = reviewContent, imeAction = ImeAction.Done, onAction = KeyboardActions {
                keyboardController?.hide()
            })

            val isValid = reviewTitle.value.isNotEmpty() && reviewContent.value.isNotEmpty() && rating.value !=0 && bookExist==true
            val comments = Comments(
                userId = currentUserId,
                userName = currentUserName.toString(),
                commentTitle = reviewTitle.value,
                commentContent = reviewContent.value,
                time = currentDate,
                rating = rating.value
            )

//            /*

            SubmitButton(label ="Submit", isValid =isValid){
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


@Composable
fun SubmitButton(label: String,isValid:Boolean,onClick :()->Unit={}){
    Button(onClick = {
        onClick()
    },
        colors = ButtonDefaults.buttonColors(Color.Gray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), enabled = isValid) {
        Text(text = label,
            fontWeight = FontWeight.W500,
            fontSize = 17.sp)
    }
}
@Composable
fun TextInputFiled(label: String,
                   placeholder: String,
                   textState : MutableState<String>,
                   keyboardType: KeyboardType = KeyboardType.Text,
                   imeAction : ImeAction = ImeAction.Next,
                   onAction: KeyboardActions = KeyboardActions.Default
                  ){
    Column {
        Text(text = "${label}",
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color.Gray,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
        )
        OutlinedTextField(
            value =textState.value ,
            onValueChange = {
                textState.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp, end = 20.dp, top = 5.dp),
            placeholder = {
                Text(text = placeholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
            ,   keyboardActions = onAction,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            shape = RoundedCornerShape(12.dp)
        )
    }
}