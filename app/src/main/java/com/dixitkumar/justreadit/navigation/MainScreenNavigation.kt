package com.dixitkumar.justreadit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dixitkumar.justreadit.screens.account.UserAccountScreen
import com.dixitkumar.justreadit.screens.home.HomeScreen
import com.dixitkumar.justreadit.screens.reading.ReadingScreen
import com.dixitkumar.justreadit.screens.search.SearchScreen
import com.dixitkumar.justreadit.screens.wishlist.WishlistScreen

@Composable
fun MainScreenNavigation(bottomNavController: NavHostController,readerNavigation : NavHostController){
    NavHost(navController = bottomNavController, startDestination = ReaderScreens.HomeScreen.name ){
        composable(route = ReaderScreens.HomeScreen.name){
            HomeScreen(bottomNavController = bottomNavController,readerNavigation)
        }
        
        composable(route = ReaderScreens.SearchScreen.name){
            SearchScreen(navController = bottomNavController)
        }
        composable(route = ReaderScreens.MyReadingsScreen.name){
            ReadingScreen(navController = bottomNavController)
        }
        composable(route = ReaderScreens.WishlistScreen.name){
            WishlistScreen(navController = bottomNavController)
        }
        composable(route = ReaderScreens.AccountScreen.name){
            UserAccountScreen(navController = bottomNavController)
        }
    }
}