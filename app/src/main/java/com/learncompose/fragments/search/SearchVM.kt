package com.learncompose.fragments.search

import androidx.lifecycle.ViewModel
import com.learncompose.networks.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(val repository: Repository) : ViewModel() {

}