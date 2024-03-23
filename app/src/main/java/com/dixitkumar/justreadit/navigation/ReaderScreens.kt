package com.dixitkumar.justreadit.navigation

import android.window.SplashScreen

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    SignupScreen,
    MainScreen,
    HomeScreen,
    DetailsScreen,
    MoreBookScreen,
    SearchScreen,
    MyReadingsScreen,
    WishlistScreen,
    AccountScreen;

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
