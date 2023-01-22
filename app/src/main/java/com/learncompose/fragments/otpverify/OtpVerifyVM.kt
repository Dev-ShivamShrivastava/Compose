package com.learncompose.fragments.otpverify

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpVerifyVM @Inject constructor(val firebaseAuth: FirebaseAuth):ViewModel() {
}