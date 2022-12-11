package com.learncompose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learncompose.fragments.favourite.Favourite
import com.learncompose.fragments.home.HomeFragment
import com.learncompose.fragments.login.Login
import com.learncompose.fragments.profile.Profile
import com.learncompose.fragments.search.Search
import com.learncompose.fragments.searchdetails.SearchDetails
import com.learncompose.fragments.signup.SignUpFragment
import com.learncompose.routes.BottomNavigationItems
import com.learncompose.routes.Routes
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.text.Typography

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

    isShowBottomNavigation = when (navBackStackEntry?.destination?.route) {
        Routes.Home.route, Routes.Search.route, Routes.Favourite.route, Routes.Profile.route -> true
        else -> false
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
                                restoreState = false

                            }
                        }
                    )

                }
            }
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Login.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Routes.Login.route) {
                Login(navController)
            }
            composable(Routes.SignUp.route) {
                SignUpFragment(navController)
            }
            composable(Routes.Home.route) {
                HomeFragment(navController)
            }
            composable(Routes.Search.route) {
                Search(navController)
            }
            composable(Routes.Favourite.route) {
                Favourite(navController)
            }
            composable(Routes.Profile.route) {
                Profile(navController)
            }
            composable(
                Routes.SearchDetails.route + "/{url}",
                arguments = listOf(navArgument("url") {
                    type = NavType.StringType
                })
            ) {
                val backStackEntry = it
                SearchDetails(navController, backStackEntry.arguments?.getString("url") ?: "")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Main()
}