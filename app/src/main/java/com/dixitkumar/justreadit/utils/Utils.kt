package com.dixitkumar.justreadit.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.dixitkumar.justreadit.R
import com.dixitkumar.justreadit.data.Resource
import com.dixitkumar.justreadit.model.Item
import com.dixitkumar.justreadit.screens.home.HomeScreenViewModel
import com.google.android.play.integrity.internal.al
import java.net.URL


@Composable
fun getScreenHeight() : Dp{
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    return screenHeight
}
@Composable
fun getScreenWidth() : Dp {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    return screenWidthDp
}

fun getBookRows_Tabs(): List<String>{
    return  listOf(
        "TopSearch",
        "Popular",
        "Newest",
        "Favourite"
    )
}

fun getFont() : FontFamily{
    val AclonicaFontFamily = FontFamily(
        Font(R.font.aclonica_font, FontWeight.Normal)
    )
return AclonicaFontFamily
}

@Composable
fun getBooksFromIds(booksIdsList : List<String>,viewModel :HomeScreenViewModel) : MutableList<Item>?{
    val bookList : MutableList<Item>? = mutableListOf()
    if (booksIdsList != null) {
        for (book in booksIdsList) {
            val bookItem: Item? = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                value = viewModel.getBookInfo(book)
            }.value.data
            Log.d("Liked Books" , "Getting Book Title"+bookItem?.volumeInfo?.title.toString())
            if (bookItem != null) {
                bookList?.add(bookItem)
            }
        }
    }
    if(!bookList.isNullOrEmpty()){
        return bookList
    }else{
        return null
    }
}

@Composable
fun getScreenHeight(likedList: List<String>?) : Dp{
    val currenScreenHeight = getScreenHeight()
    var screenHeight = (currenScreenHeight/100)*10
    if(likedList!=null){
      screenHeight = when(likedList.size){
            1 -> screenHeight
            2 -> screenHeight*2
            3 -> screenHeight*3
            4 -> screenHeight*4
            5 -> screenHeight*5
            else -> screenHeight*5
       }
    }
    return  screenHeight
}
fun formatString(
    originalStr : String,
    slc_start:Int,
    slc_end : Int,
    replace_from : Char,
    replace_With : Char
) : String{
    val formattedString = originalStr.slice(slc_start..slc_end).replace(replace_from,replace_With)
    return formattedString
}