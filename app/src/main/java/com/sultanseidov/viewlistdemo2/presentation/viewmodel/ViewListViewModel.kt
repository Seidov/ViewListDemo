package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.viewlistdemo2.data.model.pinviewlist.PinViewListModel
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewListViewModel @Inject constructor(
    private val repository: IMovieRepository
) : ViewModel() {

    private val _pins = MutableStateFlow<List<PinViewListModel>>(emptyList())
    val pins: StateFlow<List<PinViewListModel>> = _pins

    fun getAllPins() {
        viewModelScope.launch {
            repository.getAllPinsViewList().collect {
                _pins.value = it
            }
        }
    }
}
