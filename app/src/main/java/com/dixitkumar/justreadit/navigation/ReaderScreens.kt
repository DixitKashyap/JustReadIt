package com.dixitkumar.justreadit.navigation

import android.window.SplashScreen

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    SignupScreen,
    MainScreen,
    HomeScreen,
    DetailsScreen,
    AllCommentScreen,
    ReviewScreen,
    MoreBookScreen,
    SearchScreen,
    MyReadingsScreen,
    AccountScreen,
    AboutTheAppScreen,
    EditAccountScreens;

    companion object{
        fun fromRoute(route:String?) : ReaderScreens
        = when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            SignupScreen.name -> SignupScreen
            HomeScreen.name -> HomeScreen
            DetailsScreen.name -> DetailsScreen
            MoreBookScreen.name -> MoreBookScreen

            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}
