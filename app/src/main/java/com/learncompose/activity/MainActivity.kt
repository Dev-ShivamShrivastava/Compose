package com.learncompose.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learncompose.fragments.home.HomeFragment
import com.learncompose.fragments.login.Login
import com.learncompose.fragments.signup.signUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            Login(navController)
        }
        composable("Signup") {
            signUpFragment(navController)
        }
        composable("Home") {
            HomeFragment(navController)
        }
    }

    Handler(Looper.myLooper()!!).postDelayed(Runnable {
        navController.navigate("Home"){
            launchSingleTop = true
        }
    },2000)
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting()
}