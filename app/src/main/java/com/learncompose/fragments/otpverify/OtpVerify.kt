package com.learncompose.fragments.otpverify

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.learncompose.activity.MainActivity
import com.learncompose.common.CommonButton
import com.learncompose.routes.Routes
import com.learncompose.utils.getActivity
import com.learncompose.utils.showToast
import java.util.concurrent.TimeUnit


var verificationId = ""

@Composable
fun OtpVerify(navController: NavHostController,phoneNo:String) {
    val viewModel = hiltViewModel<OtpVerifyVM>()
    val context = LocalContext.current
    val otp = remember {
        mutableStateOf("")
    }

    // callback method is called on Phone auth provider.
// initializing our callbacks for on
// verification callback method.
    val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    otp.value = code
                    VerifyCode(otp.value, viewModel, context, navController);
                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Log.d("error->", e.message ?: "otp can't sent now")

                context.showToast(e.message ?: "exception")
            }
        }

    LaunchedEffect(key1 = 1) {
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(viewModel.firebaseAuth)
            .setPhoneNumber("+91${phoneNo}") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context.getActivity<MainActivity>() as Activity) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
    OtpUI(otp,phoneNo) {
        VerifyCode(otp.value, viewModel, context, navController);
    }


}

@Composable
fun OtpUI(
    otp: MutableState<String>,phoneNo: String,
    verifyOtp: () -> Unit
) {
    LocalContext.current.showToast(phoneNo)
    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        Text(
            text = "Otp Verify",
            fontSize = 30.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.wrapContentSize(),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Otp sent on ${phoneNo}",
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.wrapContentSize(),
        )

        Spacer(modifier = Modifier.height(120.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            repeat(6) { index ->
                val temp = remember {
                    mutableStateOf("")
                }
                val focusManager = LocalFocusManager.current

                val focusRequester = remember { FocusRequester() }
                val isFocused = remember { mutableStateOf(false)  }

                if (otp.value.length < 6) {
                    isFocused.value = index >= otp.value.length
                }
                BasicTextField(
                    singleLine = true, value = temp.value, onValueChange = {
                        if (it.length < 2) {
                            otp.value = otp.value + it
                            temp.value = it
                        }

                    }, modifier = Modifier
                        .width(50.dp)
                        .height(IntrinsicSize.Min)
                        .border(
                            1.dp, Color.Gray,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(0.dp, 10.dp), textStyle = TextStyle.Default.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ), keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    })
                )

                Spacer(modifier = Modifier.width(10.dp))

            }
        }

        CommonButton(onClick = { verifyOtp() })


    }

}


private fun VerifyCode(
    code: String,
    viewModel: OtpVerifyVM,
    context: Context,
    navController: NavHostController
) {
    // below line is used for getting
    // credentials from our verification id and code.
    val credential = PhoneAuthProvider.getCredential(verificationId, code)

    // after getting credential we are
    // calling sign in method.
    SignInWithCredential(credential, viewModel, context, navController)
}

private fun SignInWithCredential(
    credential: PhoneAuthCredential,
    viewModel: OtpVerifyVM,
    context: Context,
    navController: NavHostController
) {
    // inside this method we are checking if
    // the code entered is correct or not.
    viewModel.firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                // if the code is correct and the task is successful
                // we are sending our user to new activity.
                context.showToast(
                    "Login"
                )
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.OtpVerify.route) {
                        inclusive = true
                    }
                }

            } else {
                // if the code is not correct then we are
                // displaying an error message to the user.
                context.showToast(
                    task.exception?.message ?: "Something went wrong"
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    OtpUI(otp = remember {
        mutableStateOf("")
    },"") {

    }
}