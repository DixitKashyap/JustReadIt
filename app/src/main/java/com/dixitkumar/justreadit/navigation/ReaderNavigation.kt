package com.dixitkumar.justreadit.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dixitkumar.justreadit.screens.MainScreen
import com.dixitkumar.justreadit.screens.SplashScreen
import com.dixitkumar.justreadit.screens.account.AboutScreen
import com.dixitkumar.justreadit.screens.account.EditAccountScreen
import com.dixitkumar.justreadit.screens.details.AllCommentScreen
import com.dixitkumar.justreadit.screens.details.BookReviewScreen
import com.dixitkumar.justreadit.screens.details.Book_DetailsScreen
import com.dixitkumar.justreadit.screens.home.HomeScreen
import com.dixitkumar.justreadit.screens.login.LoginScreen
import com.dixitkumar.justreadit.screens.morebooks.More_BooksScreen
import com.dixitkumar.justreadit.screens.search.SearchScreen
import com.dixitkumar.justreadit.screens.signup.SignUpScreen

@RequiresApi(Build.VERSION_CODES.O)
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

        val moreBooks = ReaderScreens.MoreBookScreen.name
        composable(route= "$moreBooks/{moreDetails}",arguments = listOf(navArgument("moreDetails"){
            type = NavType.StringType
        })){backStackEntry->
            backStackEntry.arguments?.getString("moreDetails").let{
                More_BooksScreen(navController = navController,moreDetails = it.toString())
            }
        }
        
        composable(route = ReaderScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }

        val reviewScreen = ReaderScreens.ReviewScreen.name
        composable(route="$reviewScreen/{bookImageUrl}", arguments = listOf(navArgument("bookImageUrl"){
            type = NavType.StringType
        })){
            backStackEntry ->
            backStackEntry.arguments?.getString("bookImageUrl").let{
                BookReviewScreen(navController = navController, bookId = it.toString())
            }
        }

        val allCommentsScreen = ReaderScreens.AllCommentScreen.name
        composable(route = "$allCommentsScreen/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){
            backStackEntry->
            backStackEntry.arguments?.getString("bookId").let {
                AllCommentScreen(navController = navController,bookId = it.toString())
            }
        }
        
        composable(route=ReaderScreens.AboutTheAppScreen.name){
            AboutScreen(navController = navController)
        }
        
        composable(route = ReaderScreens.EditAccountScreens.name){
            EditAccountScreen(navController = navController)
        }
    }
}