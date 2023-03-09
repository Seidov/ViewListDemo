package com.sultanseidov.viewlistdemo2.viewmodel

import androidx.lifecycle.ViewModel
import com.sultanseidov.viewlistdemo2.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(
    private val repository: Repository
):ViewModel(){

}