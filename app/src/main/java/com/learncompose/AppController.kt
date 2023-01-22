package com.learncompose

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AppController:Application() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate() {
        super.onCreate()
        mAuth = FirebaseAuth.getInstance();
    }

}