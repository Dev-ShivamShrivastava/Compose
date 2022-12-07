package com.learncompose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.learncompose.fragments.favourite.Favourite
import com.learncompose.fragments.home.HomeFragment
import com.learncompose.fragments.login.Login
import com.learncompose.fragments.profile.Profile
import com.learncompose.fragments.search.Search
import com.learncompose.fragments.signup.signUpFragment
import com.learncompose.models.BottomNavigationItems
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.meta.When

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    val bottomItemList = listOf<BottomNavigationItems>(
        BottomNavigationItems.Home, BottomNavigationItems.Search, BottomNavigationItems.Favourite,
        BottomNavigationItems.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var isShowBottomNavigation by rememberSaveable { mutableStateOf(false) }


    isShowBottomNavigation = when(navBackStackEntry?.destination?.route){
        "Login" -> false
        "Signup" -> false
        else -> true
    }


    Scaffold(
        bottomBar = {
           if (isShowBottomNavigation) BottomNavigation {
                val currentDestination = navBackStackEntry?.destination
                bottomItemList.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(screen.icon, contentDescription = null)
                        },
                        label = {
                            Text(stringResource(id = screen.resourceID))
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                launchSingleTop = true
                                restoreState = true

                            }
                        }
                    )

                }
            }
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Login",
            Modifier.padding(innerPadding)
        ) {
            composable("Login") {
                Login(navController)
            }
            composable("Signup") {
                signUpFragment(navController)
            }
            composable(BottomNavigationItems.Home.route) {
                HomeFragment(navController)
            }
            composable(BottomNavigationItems.Search.route) {
                Search(navController)
            }
            composable(BottomNavigationItems.Favourite.route) {
                Favourite(navController)
            }
            composable(BottomNavigationItems.Profile.route) {
                Profile(navController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Main()
}