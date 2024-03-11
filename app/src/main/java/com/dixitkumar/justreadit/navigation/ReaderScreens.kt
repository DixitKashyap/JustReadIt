package com.dixitkumar.justreadit.navigation

import android.window.SplashScreen

enum class ReaderScreens {
    LoginScreen,
    SignupScreen,
    HomeScreen;

    companion object{
        fun fromRoute(route:String?) : ReaderScreens
        = when(route?.substringBefore("/")){
            LoginScreen.name -> LoginScreen
            SignupScreen.name -> SignupScreen
            HomeScreen.name -> HomeScreen

            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}
