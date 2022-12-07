package com.learncompose.fragments.profile

import androidx.lifecycle.ViewModel
import com.learncompose.networks.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(val repository: Repository) : ViewModel() {
}