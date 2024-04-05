package com.dixitkumar.justreadit.screens.search

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.navigation.ReaderScreens
import com.dixitkumar.justreadit.utils.getBookRows_Tabs
import com.dixitkumar.justreadit.utils.getScreenWidth

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
        "BOOKS",
        "AUTHORS",
        "CATEGORY"
    )
    val selectedItem = remember{
        mutableStateOf(0)
    }
    Surface(modifier = Modifier
        .fillMaxSize(), color = Color.White) {
        Column {
                Row (modifier = Modifier
                    .fillMaxWidth() ,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    SearchArea(searchQuery = searchQuery,navController = navController){searchQuery->
                        viewModel.searchQuery(searchQuery)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(imageVector = Icons.Default.Mic,
                        contentDescription = "Mic Button",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(3.dp)
                            .clickable {

                            })
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
                                fontSize = 14.sp)})
                    }
                }
                HorizontalBookOptionsRow(getBookRows_Tabs())
                SearchedBook(list = viewModel.default_list,navController=navController)
            }
    }
}

@Composable
fun HorizontalBookOptionsRow(list : List<String>) {
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){
        LazyRow {
            items(list){list_item->
                RowButton(button_text = list_item)
            }
        }
    }
}


@Composable
fun RowButton(button_text : String,onClick:()->Unit={}){
    Button(onClick = {
        onClick()
                     },
        modifier= Modifier
            .height(50.dp)
            .wrapContentWidth()
            .padding(5.dp)
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(Color.White)) {
        Text(text = "${button_text}",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
          )
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
    val screenWidth = getScreenWidth()
    Row(modifier = Modifier
        .width((screenWidth / 100) * 80)
        .border(
            width = 2.dp,
            color = Color.Gray,
            shape = RoundedCornerShape(10.dp)
        )
        .padding(start = 12.dp, end = 12.dp)
       ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(27.dp)
                .clickable {
                    if (navController.previousBackStackEntry != null) {
                        val previousRoute =
                            navController.previousBackStackEntry?.destination?.route.toString()
                        navController.navigate(route = navController.previousBackStackEntry?.destination?.route!!)
                        navController.popBackStack(previousRoute, false)
                    }
                })
        Spacer(modifier = Modifier.width(15.dp))
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
                    , fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    color = colorResource(id = R.color.blue),
                    overflow = TextOverflow.Ellipsis)

                Text(text = "Pages : ${bookDetailsState?.volumeInfo?.pageCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.blue))
                Text(text = "Published : ${bookDetailsState?.volumeInfo?.publishedDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
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
        ,modifier= modifier
            .height(50.dp)
            .fillMaxWidth(),
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
                    }else{

                    }
                })
        }
    )

}