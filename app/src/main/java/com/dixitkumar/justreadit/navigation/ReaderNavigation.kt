package com.dixitkumar.justreadit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dixitkumar.justreadit.screens.login.LoginScreen

@Composable
fun ReaderNavigation(){
    
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = ReaderScreens.LoginScreen.name){
        composable(route = ReaderScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
    }
}