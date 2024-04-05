package com.dixitkumar.justreadit.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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