package com.dixitkumar.justreadit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dixitkumar.justreadit.screens.MainScreen
import com.dixitkumar.justreadit.screens.SplashScreen
import com.dixitkumar.justreadit.screens.details.Book_DetailsScreen
import com.dixitkumar.justreadit.screens.home.HomeScreen
import com.dixitkumar.justreadit.screens.login.LoginScreen
import com.dixitkumar.justreadit.screens.signup.SignUpScreen

@Composable
fun ReaderNavigation(){
    
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(route = ReaderScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(route = ReaderScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        
        composable(route = ReaderScreens.SignupScreen.name){
            SignUpScreen(navController = navController)
        }

        composable(route = ReaderScreens.MainScreen.name){
            MainScreen(readerNavController = navController)
        }

        val details = ReaderScreens.DetailsScreen.name
        composable(route = "$details/{bookId}",arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let{
                Book_DetailsScreen(navController = navController,bookId = it.toString())
            }
        }
    }
}