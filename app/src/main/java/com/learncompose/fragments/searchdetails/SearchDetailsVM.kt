package com.learncompose.fragments.searchdetails

import androidx.lifecycle.ViewModel
import com.learncompose.networks.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchDetailsVM @Inject constructor(val repository: Repository):ViewModel() {
}