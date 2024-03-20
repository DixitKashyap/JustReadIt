package com.dixitkumar.justreadit.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.dixitkumar.justreadit.navigation.ReaderScreens

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label : String
)

fun getBottomNavItem() :List<BottomNavItem>{
    return listOf(
            BottomNavItem(
                title = ReaderScreens.HomeScreen.name,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                label = "Home"
            ),
    BottomNavItem(
        title = ReaderScreens.SearchScreen.name,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        label = "Search"

    ),
    BottomNavItem(
        title = ReaderScreens.MyReadingsScreen.name,
        selectedIcon = Icons.Filled.LibraryBooks,
        unselectedIcon = Icons.Outlined.LibraryBooks,
        label = "Reading"
    ),
        BottomNavItem(
            title = ReaderScreens.WishlistScreen.name,
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            label = "Wishlist"
        ),
        BottomNavItem(
            title = ReaderScreens.AccountScreen.name,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            label = "Account"
        )
    )
}