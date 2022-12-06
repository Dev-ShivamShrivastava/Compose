package com.learncompose.fragments.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor():ViewModel() {
    var data = MutableLiveData("")

    fun showToast(context: Context){
        Toast.makeText(context,"Working fine",Toast.LENGTH_LONG).show()
    }
}