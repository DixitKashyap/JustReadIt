package com.dixitkumar.justreadit.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dixitkumar.justreadit.model.getBottomNavItem
import com.dixitkumar.justreadit.navigation.MainScreenNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(readerNavController: NavHostController){
    val bottomNavController = rememberNavController()
    val bottomNavItems = getBottomNavItem()
    var selectedItemIndex = rememberSaveable {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                Card (elevation = CardDefaults.cardElevation(12.dp)){
                    NavigationBar(containerColor = Color.White, tonalElevation = 20.dp) {
                        bottomNavItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex.value == index,
                                onClick = {
                                    selectedItemIndex.value = index
                                    bottomNavController.navigate(item.title) {

                                        bottomNavController.graph.startDestinationRoute?.let { screen_route ->
                                            popUpTo(screen_route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }, label = {
                                    Text(
                                        text = item.label
                                    )
                                },
                                alwaysShowLabel = true,
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex.value) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.White
                                ))
                        }
                    }
                }
            }
        ) {
           MainScreenNavigation(bottomNavController =bottomNavController,readerNavController)
        }

    }
}