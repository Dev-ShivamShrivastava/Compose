package com.learncompose.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.learncompose.R

sealed class BottomNavigationItems(val route:String, @StringRes val resourceID:Int, val icon : ImageVector){
    object Home:BottomNavigationItems("Home", R.string.Home, Icons.Filled.Home)
    object Search:BottomNavigationItems("Search", R.string.Search, Icons.Filled.Search)
    object Favourite:BottomNavigationItems("Favourite", R.string.Favourite, Icons.Filled.Favorite)
    object Profile:BottomNavigationItems("Profile", R.string.Profile, Icons.Filled.Person)
}
