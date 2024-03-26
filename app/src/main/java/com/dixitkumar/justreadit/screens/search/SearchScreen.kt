package com.dixitkumar.justreadit.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.screens.home.HomeScreenViewModel

@Composable
fun SearchScreen(navController: NavController){
    SearchScreenUi(navController=navController)
}


@Composable
fun SearchScreenUi(viewModel: SearchScreenViewModel = hiltViewModel(),navController: NavController){

    val searchQuery = rememberSaveable{
        mutableStateOf("")
    }
    val tabItemList = listOf(
        "Books",
        "Author",
        "Category"
    )
    val selectedItem = remember{
        mutableStateOf(0)
    }
    Surface(modifier = Modifier
        .fillMaxSize(), color = Color.White) {
        Column {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                    ){
                    SearchArea(searchQuery = searchQuery){searchQuery->
                        viewModel.searchQuery(searchQuery)
                    }
                }
                TabRow(selectedTabIndex = selectedItem.value,
                    containerColor = Color.White) {
                    tabItemList.forEachIndexed { index, s ->
                        Tab(selected =index == selectedItem.value,
                            onClick = {
                                     selectedItem.value = index
                                if(selectedItem.value == 0){
                                    viewModel.searchQuery(searchQuery.value)
                                }
                                else if(selectedItem.value == 1){
                                    viewModel.searchQuery("author=${searchQuery.value}")
                                }else if(selectedItem.value == 2){
                                    viewModel.searchQuery("subject:${searchQuery.value}")
                                }
                            },
                            text = { Text(text = "${tabItemList[index]}",
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 16.sp)})
                    }
                }
                SearchedBook(list = viewModel.default_list,navController=navController)
            }
    }
}

@Composable
fun SearchArea(
    searchQuery :MutableState<String>,
    onSearch :(String)->Unit = {}
){
    val valid = remember(searchQuery.value) {
        searchQuery.value.trim().isNotBlank()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.DarkGray,
            modifier = Modifier.size(27.dp))
        InputFiled(valueState =searchQuery ,
            labelId ="Search here" ,
            enabled = true,
            imeAction = ImeAction.Search,
            onAction = KeyboardActions {
                if(!valid)return@KeyboardActions
                onSearch(searchQuery.value.toString().trim())
                keyboardController?.hide()
            })
    }
}

@Composable
fun SearchedBook(list :List<Item>,navController: NavController){
    if(list.isEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "No Book Found")
        }
    }else {
        LazyColumn {
            items(list) {
                SearchedItem(bookDetailsState = it, navController =navController )
            }
        }
    }
}
@Composable
fun SearchedItem(bookDetailsState : Item?,navController: NavController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {
            navController.navigate(route = ReaderScreens.DetailsScreen.name + "/${bookDetailsState?.id}")
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start){
        AsyncImage(model = bookDetailsState?.volumeInfo?.imageLinks?.thumbnail,
            contentDescription = "Book Image", placeholder = painterResource(id = R.drawable.splash_image),
            modifier = Modifier
                .width(80.dp)
                .height(120.dp),
            contentScale = ContentScale.Crop)
            Column (modifier = Modifier
                .padding(start = 12.dp, bottom = 12.dp)
                .height(115.dp)){
                Text(text = bookDetailsState?.volumeInfo?.title.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 3,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis)
                Text(text = "by ${bookDetailsState?.volumeInfo?.authors}"
                    , fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    color = colorResource(id = R.color.blue),
                    overflow = TextOverflow.Ellipsis)

                Text(text = "Pages : ${bookDetailsState?.volumeInfo?.pageCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.blue))
                Text(text = "Published : ${bookDetailsState?.volumeInfo?.publishedDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.blue))
                HorizontalDivider(Modifier.width(2.dp), color = Color.LightGray)
            }
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
        , textStyle = TextStyle(fontSize = 15.sp, background = Color.White)
        ,modifier= modifier
            .height(50.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape),
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
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search Icon")
        }
    )

}