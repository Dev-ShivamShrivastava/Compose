package com.learncompose.fragments.search

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.size.Scale
import com.learncompose.routes.Routes
import com.learncompose.utils.OnLifecycleEvent
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun Search(navController: NavController) {
    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                Log.e("Lifecycle-->", "ON_START")
            }
            Lifecycle.Event.ON_CREATE -> {
                Log.e("Lifecycle-->", "ON_CREATE")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.e("Lifecycle-->", "ON_RESUME")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.e("Lifecycle-->", "ON_PAUSE")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.e("Lifecycle-->", "ON_STOP")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.e("Lifecycle-->", "ON_DESTROY")
            }
            Lifecycle.Event.ON_ANY -> {
                Log.e("Lifecycle-->", "ON_ANY")
            }
        }
    }
    Main(navController)
    BackHandler(true) {
        navController.navigate(Routes.Home.route)
    }
}


@Composable
fun Main(navController: NavController?) {
    val viewModel = hiltViewModel<SearchVM>()
    if (viewModel.isShowLoader.value) ProgressBar()
    if (viewModel.newsResponse.value.articles?.isNotEmpty() == true) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            val list = remember {
                mutableStateOf(
                    viewModel.newsResponse.value.articles
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(list.value?.size ?: 0) { position ->
                    val data = list.value?.get(position)
                    val context = LocalContext.current
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .padding(0.dp)
                            .clickable {
                                val url = data?.url
                                val encodedUrl = URLEncoder.encode(
                                    url,
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController?.navigate(Routes.SearchDetails.route + "/$encodedUrl")
                            },
                    ) {
                        Column() {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = list.value?.get(
                                        position
                                    )?.urlToImage,
                                    placeholder = painterResource(id = com.learncompose.R.drawable.ic_image),
                                    error = painterResource(
                                        id = com.learncompose.R.drawable.ic_broken_image
                                    ),
                                    contentScale = ContentScale.Crop
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                                    .size(200.dp)
                            )


                            Text(
                                text = data?.title ?: "",
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                color = Color.Black,
                                fontSize = TextUnit.Unspecified
                            )
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun ProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Main(null)
}
