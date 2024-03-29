package com.learncompose.fragments.home

import android.R
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.learncompose.utils.OnLifecycleEvent
import com.learncompose.utils.toArrayList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeFragment(onNavigate: NavHostController) {
    val viewModel = hiltViewModel<HomeVM>()
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
    HomeScreen(viewModel)

}

@Composable
fun HomeScreen(viewModel: HomeVM) {
    val isSingleSelected = remember {
        mutableStateOf(false)
    }


    if (viewModel.isShowLoader.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator()
        }
    }


    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = "Multiple Selection",
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        if (isSingleSelected.value) Color.LightGray else Color.Black
                    )
                    .clickable {
                        isSingleSelected.value = false
                    }
                    .padding(10.dp),
                color = if (isSingleSelected.value) Color.Black else Color.White)

            Text(
                text = "Single Selection", modifier = Modifier
                    .padding(10.dp)
                    .background(
                        if (isSingleSelected.value) Color.Black else Color.LightGray
                    )
                    .padding(10.dp)
                    .clickable {
                        isSingleSelected.value = true
                    }, color = if (isSingleSelected.value) Color.White else Color.Black
            )
        }
        if (isSingleSelected.value) {
            SetUpRecyclerView(isSingleSelected.value, viewModel) {
                viewModel.isShowLoader.value = it
            }
        } else {
            SetUpRecyclerView(isSingleSelected.value, viewModel) {
                viewModel.isShowLoader.value = it
            }
        }
    }


}

@Composable
fun SetUpRecyclerView(isSingleSelected: Boolean, viewModel: HomeVM, hide: (Boolean) -> Unit) {


    if (viewModel.weatherData.value.dataseries?.isNotEmpty() == true) {
        ShowSnakeBar()

        hide(false)

        var isShowDialog = remember {
            mutableStateOf(true)
        }

        val list = remember {
            mutableStateOf(
                viewModel.weatherData.value.dataseries
            )
        }

        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {

            items(list.value?.size ?: 0) { pos ->
                val isLongPress = remember {
                    mutableStateOf(false)
                }
                Card(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    isLongPress.value = true
                                    isShowDialog.value = true
                                },
                                onTap = {
                                    if (isSingleSelected) {
                                        list.value = list.value
                                            ?.mapIndexed { index, homeDataModel ->
                                                if (pos == index) {
                                                    homeDataModel.copy(isSelected = !homeDataModel.isSelected)
                                                } else {
                                                    homeDataModel.copy(isSelected = false)
                                                }
                                            }
                                            ?.toArrayList()
                                    } else {
                                        list.value = list.value
                                            ?.mapIndexed { index, homeDataModel ->
                                                if (pos == index) {
                                                    homeDataModel.copy(isSelected = !homeDataModel.isSelected)
                                                } else {
                                                    homeDataModel
                                                }
                                            }
                                            ?.toArrayList()
                                    }
                                }
                            )
                        }
                        .fillMaxWidth()
                        .fillMaxHeight(100f)
                        .padding(10.dp, 5.dp),
                    elevation = 2.dp,
                    backgroundColor = if (list.value?.get(pos)?.isSelected == true) Color.Gray else Color.LightGray,
                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(0.dp, 15.dp)
                    ) {
                        Text(
                            text = list.value?.get(pos)?.cloudcover.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    if (isLongPress.value && isShowDialog.value) {
                        CustomDialog(value = list.value?.get(pos)?.cloudcover.toString(), { it ->
                            isShowDialog.value = it
                        }, {
                            list.value = list.value
                                ?.mapIndexed { index, homeDataModel ->
                                    if (pos == index) {
                                        isLongPress.value = false
                                        homeDataModel.copy(cloudcover = it.toInt())
                                    } else {
                                        homeDataModel.copy()
                                    }
                                }
                                ?.toArrayList()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSnakeBar() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) { inputPadding ->
            LaunchedEffect(key1 = "SnakeBar") {
                coroutineScope.launch {
                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = "This is your message",
                        actionLabel = "Do something", SnackbarDuration.Short
                    )
                    when (snackbarResult) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> {}
                    }
                }
            }
    }
    
}


@Composable
fun CustomDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }

    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Edit value",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "",
                            tint = Color.Cyan,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "",
                                tint = Color.Green,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        placeholder = { Text(text = "Enter value") },
                        value = txtField.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            txtField.value = it.take(10)
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (txtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@Button
                                }
                                setValue(txtField.value)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Done")
                        }
                    }
                }
            }
        }
    }
}

