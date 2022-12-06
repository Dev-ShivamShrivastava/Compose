package com.learncompose.fragments.login

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.learncompose.R
import com.learncompose.utils.OnLifecycleEvent

@Composable
fun Login(navController: NavController, viewModel: LoginVM = viewModel<LoginVM>()) {

//    val viewModel = hiltViewModel<LoginVM>()
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
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val emailValue = remember { mutableStateOf(TextFieldValue()) }
        val passwordValue = remember { mutableStateOf(TextFieldValue()) }
        val isShowForgotPassword = remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        isShowForgotPassword.value = !isShowForgotPassword.value
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(20.dp, 0.dp)
                        .padding(0.dp, 10.dp)
                        .align(Alignment.End), contentPadding = PaddingValues(
                        0.dp, 15.dp
                    ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ) {
                    Text(text = "Skip")
                }

                Text(
                    text = "Compose Learning",
                    Modifier
                        .padding(0.dp, 100.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = emailValue.value,
                    onValueChange = { emailValue.value = it },
                    placeholder = {
                        Text(
                            text = "Please enter your email address"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                TextField(
                    value = passwordValue.value,
                    onValueChange = { passwordValue.value = it },
                    placeholder = {
                        Text(
                            text = "Please enter password"
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password),
                            contentDescription = "",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )

                if (isShowForgotPassword.value) {
                    Text(
                        text = "Forgot Password?",
                        Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(20.dp, 0.dp)
                            .padding(0.dp, 10.dp)
                            .align(Alignment.End),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("Home")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 10.dp), contentPadding = PaddingValues(
                        0.dp, 15.dp
                    )
                ) {
                    Text(text = "Login")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(0.dp, 10.dp)
                    .padding(0.dp, 10.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Don't have an account? Sign up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("Signup")
                    }
                )

            }
        }


    }
}