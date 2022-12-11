package com.learncompose.fragments.signup

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.learncompose.R
import com.learncompose.utils.OnLifecycleEvent
import java.util.*

@Composable
fun SignUpFragment(navController: NavController) {
    val emailValue = remember { mutableStateOf(TextFieldValue()) }
    val passwordValue = remember { mutableStateOf(TextFieldValue()) }
    val confirmPasswordValue = remember { mutableStateOf(TextFieldValue()) }

    OnLifecycleEvent{ owner, event ->
        when(event){
            Lifecycle.Event.ON_START ->{
                Log.e("Lifecycle-->","ON_START")
            }
            Lifecycle.Event.ON_CREATE ->{
                Log.e("Lifecycle-->","ON_CREATE")
            }
            Lifecycle.Event.ON_RESUME->{
                Log.e("Lifecycle-->","ON_RESUME")
            }
            Lifecycle.Event.ON_PAUSE ->{
                Log.e("Lifecycle-->","ON_PAUSE")
            }
            Lifecycle.Event.ON_STOP ->{
                Log.e("Lifecycle-->","ON_STOP")
            }
            Lifecycle.Event.ON_DESTROY ->{
                Log.e("Lifecycle-->","ON_DESTROY")
            }
            Lifecycle.Event.ON_ANY ->{
                Log.e("Lifecycle-->","ON_ANY")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

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

            TextField(
                value = confirmPasswordValue.value,
                onValueChange = { confirmPasswordValue.value = it },
                placeholder = {
                    Text(
                        text = "Please enter confirm password"
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

            val mContext = LocalContext.current
            val mYear: Int
            val mMonth: Int
            val mDay: Int

            val mCalendar = Calendar.getInstance()

            // Fetching current year, month and day
            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH)
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

            mCalendar.time = Date()

            // Declaring a string value to
            // store date in string format
            val mDate = remember { mutableStateOf("") }

            // Declaring DatePickerDialog and setting
            // initial values as current values (present year, month and day)
            val mDatePickerDialog = DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
                }, mYear, mMonth, mDay
            )


            TextField(
                value = mDate.value,
                onValueChange = { mDate.value = it }, enabled = false,
                placeholder = {
                    Text(
                        text = "Please enter Date of Birth"
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
                    .clickable {
                        mDatePickerDialog.show()
                    },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Button(
                onClick = {navController.popBackStack()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp), contentPadding = PaddingValues(
                    0.dp, 15.dp
                )
            ) {
                Text(text = "Register")
            }

        }

        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp, 10.dp)
                .padding(0.dp, 10.dp)
        ) {
            Text(
                text = "Already have an account? Sign in",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
        }

    }

}