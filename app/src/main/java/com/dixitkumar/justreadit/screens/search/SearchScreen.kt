package com.dixitkumar.justreadit.screens.search

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.home.BookItems
import com.dixitkumar.justreadit.screens.wishlist.FirebaseViewModel
import com.dixitkumar.justreadit.ui.theme.lightBlue
import com.dixitkumar.justreadit.utils.GetFirebaseUserData
import com.dixitkumar.justreadit.utils.getCurrentUserId
import com.dixitkumar.justreadit.utils.getScreenWidth
import java.util.logging.Handler

@Composable
fun SearchScreen(navController: NavController){
        SearchScreenUi(navController=navController)
}


@Composable
fun SearchScreenUi(viewModel: SearchScreenViewModel = hiltViewModel(), firebaseViewModel: FirebaseViewModel= hiltViewModel(), navController: NavController){

    val searchQuery = rememberSaveable{
        mutableStateOf("")
    }
    val tabItemList = listOf(
        "BOOKS",
        "AUTHORS",
        "CATEGORY"
    )
    val selectedItem = remember{
        mutableStateOf(0)
    }

    val showProgress = remember{ mutableStateOf(true) }

    android.os.Handler().postDelayed({
        showProgress.value = false
    },3000)
    val recentSearchList = GetFirebaseUserData(viewModel= firebaseViewModel)?.recentSearched
    Surface(modifier = Modifier
        .fillMaxSize(), color = Color.White) {
        Column{
                Row (modifier = Modifier
                    .fillMaxWidth() ,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    SearchArea(searchQuery = searchQuery,navController = navController){searchQuery->
                        viewModel.searchQuery(searchQuery)
                        viewModel.suggested_book(searchQuery)
                        SearchQueryToDb(query = searchQuery,viewModel= firebaseViewModel)

                    }
                }
                TabRow(selectedTabIndex = selectedItem.value,
                    containerColor = Color.White) {
                    tabItemList.forEachIndexed { index, s ->
                        Tab(selected =index == selectedItem.value,
                            onClick = {
                                     selectedItem.value = index
                                when (selectedItem.value) {
                                    0 -> {
                                        viewModel.searchQuery(searchQuery.value)
                                        viewModel.suggested_book(searchQuery.value)
                                        //Adding Search Query TO Db
                                    }
                                    1 -> {
                                        viewModel.searchQuery("author=${searchQuery.value}")
                                    }
                                    2 -> {
                                        viewModel.searchQuery("subject:${searchQuery.value}")
                                    }
                                }
                            },
                            text = { Text(text = tabItemList[index],
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 14.sp)})
                    }
                }

                if(viewModel.default_list.isNotEmpty() && viewModel.suggestedList.isNotEmpty()) {
                    SearchedBook(list1 = viewModel.default_list,list2 = viewModel.suggestedList, list3 = recentSearchList, navController = navController)
                }else{
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        if(showProgress.value){
                            CircularProgressIndicator(color = lightBlue)
                        }else{
                            Image(painter = painterResource(id = R.drawable.file_not_found),
                                contentDescription ="No Search Image Found" ,
                                contentScale = ContentScale.Crop)

                            Text(text = "No Book Found",
                                color = Color.DarkGray,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }
                }
            }
    }
}
fun SearchQueryToDb(query : String,viewModel: FirebaseViewModel){
    val current = getCurrentUserId()


    viewModel.getDocumentReference("users","userId", current){documentRef->

        viewModel.getFieldAsList("users",documentRef.toString(),"recentSearched"){
            var contentList = it as MutableList<String>
            if(it.isEmpty()){
                contentList = mutableListOf(query)
                viewModel.updateField("users",documentRef.toString(),"recentSearched",contentList)
            }
            else if(!contentList.isNullOrEmpty() && contentList?.size!! <=10){
                contentList?.add(query)
                viewModel.updateField("users",documentRef.toString(),"recentSearched",contentList)
            }else if(!contentList.isNullOrEmpty() && contentList.size>=10){
                contentList.removeAt(0)
                contentList.add(query)
                viewModel.updateField("users",documentRef.toString(),"recentSearched",contentList)


            }
        }
    }
}
@Composable
fun SearchArea(
    searchQuery:MutableState<String>,
    navController: NavController,
    onSearch:(String)->Unit = {},
){
    val valid = remember(searchQuery.value) {
        searchQuery.value.trim().isNotBlank()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    getScreenWidth()
    Row(modifier = Modifier
        .fillMaxWidth()
        .border(
            width = 2.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(10.dp)
        )
        .padding(start = 12.dp, end = 12.dp)
       ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(27.dp)
                .clickable {
                    navController.popBackStack()
                })
        Spacer(modifier = Modifier.width(15.dp))
            InputFiled(valueState =searchQuery ,
                labelId ="Search here" ,
                enabled = true,
                imeAction = ImeAction.Search,
                onAction = KeyboardActions {
                    if(!valid)return@KeyboardActions
                    onSearch(searchQuery.value.trim())
                    keyboardController?.hide()
                })

    }
}


@Composable
fun SearchedBook(list1 :List<Item>,list2:List<Item>,list3 : List<String>? = null,navController: NavController){
        Column(modifier = Modifier
            .wrapContentHeight()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 12.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Top Searches",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            LazyRow(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)) {
                items(list1) {
                    BookItems(book = it, readerNavController = navController)
                }
            }

            LazyColumn(modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)) {
                item {
                    Text(
                        text = "Suggested Books",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 12.dp,top=12.dp, bottom = 5.dp)
                    )
                }
                items(list2) {
                    SearchedItem(book = it, navController = navController)
                }

                if(list3!=null){
                    item {
                        Text(
                            text = "Recently Searched",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 12.dp,top=12.dp, bottom = 5.dp)
                        )
                    }

                    items(list3){
                        recentSearchItems(it)
                    }
                }

            }

        }
    }

@Composable
fun SearchedItem(book : Item?, navController: NavController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {
            navController.navigate(route = ReaderScreens.DetailsScreen.name + "/${book?.id}")
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start){
      Card(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
          val bookImageUrl = book?.volumeInfo?.imageLinks?.thumbnail

          Image(
              painter = if(bookImageUrl.isNullOrEmpty())
                  painterResource(id = R.drawable.no_img)else rememberAsyncImagePainter(
                  model = bookImageUrl
              ),
              contentDescription = "Book Image",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .width(60.dp)
                  .height(60.dp)
          )
        }
            Column (modifier = Modifier
                .padding(12.dp)
                .height(65.dp), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top){
                Text(text = book?.volumeInfo?.title.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis)
                Text(text = "by ${book?.volumeInfo?.authors}"
                    , fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    color = Color.DarkGray,
                    overflow = TextOverflow.Ellipsis)
            }
    }
}

@Composable
fun recentSearchItems(query: String,viewModel: SearchScreenViewModel = hiltViewModel()){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .clickable {
            viewModel.searchQuery(query)
            viewModel.suggested_book(query)
        },
        horizontalArrangement = Arrangement.Start){
        Icon(imageVector = Icons.Default.Search,
            contentDescription = "SearchIcons",
            tint = Color.DarkGray,
            modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = query.lowercase(),
            fontFamily = FontFamily.SansSerif,
            color = Color.DarkGray)
    }
}
@Composable
fun InputFiled(
    modifier: Modifier = Modifier,
    valueState : MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine : Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction : ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
){
    TextField(value = valueState.value,
        onValueChange = {
            valueState.value = it
        }
        , placeholder = { Text(text = labelId) }
        , singleLine = isSingleLine
        ,modifier= modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = CircleShape,
         colors = TextFieldDefaults.colors(
             focusedContainerColor = Color.White,
             unfocusedContainerColor = Color.White,
             focusedIndicatorColor = Color.Transparent,
             unfocusedIndicatorColor = Color.Transparent,
             disabledIndicatorColor = Color.Transparent
         ),
        enabled = enabled,
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        trailingIcon = {
            Icon(imageVector = if(valueState.value.isNotEmpty())Icons.Default.Clear else Icons.Default.Search, contentDescription = "Search Icon",
                modifier = Modifier.clickable {
                    if(valueState.value.isNotEmpty()){
                        valueState.value = ""
                    }
                })
        }
    )

}