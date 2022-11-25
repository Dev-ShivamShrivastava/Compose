package com.learncompose.fragments

import android.R
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.learncompose.models.HomeDataModel
import com.learncompose.utils.OnLifecycleEvent

@Composable
fun HomeFragment(onNavigate: NavHostController) {
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

    HomeScreen()


}

@Composable
fun HomeScreen() {
    val isSingleSelected = remember {
        mutableStateOf(false)
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
            SetUpRecyclerView(isSingleSelected.value)
        } else {
            SetUpRecyclerView(isSingleSelected.value)
        }
    }


}

@Composable
fun SetUpRecyclerView(isSingleSelected: Boolean) {

    val list = remember {
        mutableStateOf(
            listOf<HomeDataModel>(
                HomeDataModel(name = "Java", isSelected = false),
                HomeDataModel(name = "Python", isSelected = false),
                HomeDataModel(name = "Ruby", isSelected = false),
                HomeDataModel(name = "Kotlin", isSelected = false),
                HomeDataModel(name = "C++", isSelected = false),
                HomeDataModel(name = "c", isSelected = false),
                HomeDataModel(name = "Data Structure", isSelected = false),
                HomeDataModel(name = "Go lang", isSelected = false),
                HomeDataModel(name = "R", isSelected = false),
                HomeDataModel(name = "Dart", isSelected = false),
                HomeDataModel(name = "React js", isSelected = false),
                HomeDataModel(name = "Node js", isSelected = false),
                HomeDataModel(name = "Angular", isSelected = false),
                HomeDataModel(name = "PHP", isSelected = false),
                HomeDataModel(name = "Android", isSelected = false),
                HomeDataModel(name = "ML", isSelected = false),
                HomeDataModel(name = "C#", isSelected = false),
                HomeDataModel(name = "Swift", isSelected = false),
                HomeDataModel(name = "JavaScript", isSelected = false),
                HomeDataModel(name = "TypeScript", isSelected = false),
                HomeDataModel(name = "Scala", isSelected = false),
                HomeDataModel(name = "PowerShell", isSelected = false),
                HomeDataModel(name = "Groovy", isSelected = false),
                HomeDataModel(name = "Shell", isSelected = false),
                HomeDataModel(name = "Perl", isSelected = false),
                HomeDataModel(name = "Visual Basic .NET", isSelected = false),
                HomeDataModel(name = "SQL", isSelected = false),
                HomeDataModel(name = "Delphi", isSelected = false),
                HomeDataModel(name = "Lua", isSelected = false),
                HomeDataModel(name = "Rust", isSelected = false),
                HomeDataModel(name = "Haskell", isSelected = false),
                HomeDataModel(name = "AL", isSelected = false)
            )
        )
    }

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {

        items(list.value.size) { pos ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(100f)
                    .padding(10.dp, 5.dp)
                    .clickable {
                        if (isSingleSelected) {
                            list.value = list.value.mapIndexed { index, homeDataModel ->
                                if (pos == index) {
                                    homeDataModel.copy(isSelected = !homeDataModel.isSelected)
                                } else {
                                    homeDataModel.copy(isSelected = false)
                                }
                            }
                        } else {
                            list.value = list.value.mapIndexed { index, homeDataModel ->
                                if (pos == index) {
                                    homeDataModel.copy(isSelected = !homeDataModel.isSelected)
                                } else {
                                    homeDataModel
                                }
                            }
                        }
                    },
                elevation = 2.dp,
                backgroundColor = if (list.value[pos].isSelected) Color.Gray else Color.LightGray,
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(0.dp, 15.dp)
                ) {
                    Text(
                        text = list.value[pos].name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun preview() {
    HomeScreen()
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
                            text = "Set value",
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
                                imageVector = Icons.Filled.Home,
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

