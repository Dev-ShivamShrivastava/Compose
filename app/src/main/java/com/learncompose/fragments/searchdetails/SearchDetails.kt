package com.learncompose.fragments.searchdetails

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchDetails(navController: NavController, url: String) {
    val viewModel = hiltViewModel<SearchDetailsVM>()
    View(navController, viewModel, url)

}

@Composable
fun View(navController: NavController, viewModel: SearchDetailsVM?, url: String) {
    val isShowLoader = remember {
        mutableStateOf(true)
    }

    Box(modifier = Modifier
        .fillMaxSize(2f)) {
        if (isShowLoader.value){
            Column(Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object :WebViewClient(){
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isShowLoader.value = false
                    }
                }
//                settings.javaScriptEnabled = true
                loadUrl(url)

            }
        }, update = {
            it.loadUrl(url)
        })
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    View(rememberNavController(), null, "")
}