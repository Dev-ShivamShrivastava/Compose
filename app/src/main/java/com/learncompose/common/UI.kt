package com.learncompose.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.learncompose.fragments.login.Login

@Composable
fun CommonButton(onClick:()->Unit,text:String="Submit"){
    Button(onClick = {
        onClick()
    }, modifier = Modifier
        .fillMaxWidth()
        .padding(50.dp)
        .height(50.dp)) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun OtpTextField(){
  val otp =  remember {
        mutableStateOf("")
    }
    BasicTextField(modifier = Modifier.width(50.dp).height(50.dp), value = "sh", onValueChange ={
        otp.value = it
    }, textStyle = TextStyle.Default.copy(
        fontSize = 20.sp
    ) )
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OtpTextField()
}