package com.learncompose.fragments.favourite

import androidx.lifecycle.ViewModel
import com.learncompose.networks.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteVM @Inject constructor(val repository: Repository) : ViewModel() {

}