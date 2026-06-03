package com.sultanseidov.viewlistdemo2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.usecase.pin.WatchClassificationPipelineUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.search.GetFilteredSearchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilteredSearchResultsUseCase: GetFilteredSearchResultsUseCase,
    private val watchClassificationPipelineUseCase: WatchClassificationPipelineUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isClassifying = MutableStateFlow(false)
    val isClassifying = _isClassifying.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResults: Flow<PagingData<MovieModel>> = _searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                flowOf(PagingData.empty())
            } else {
                getFilteredSearchResultsUseCase(query)
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onListClicked(movieId: Long) {
        viewModelScope.launch {
            // Use a counter or check if already processing to avoid UI flicker
            _isClassifying.value = true
            try {
                watchClassificationPipelineUseCase(movieId)
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Classification error for movie $movieId: ${e.message}")
            } finally {
                _isClassifying.value = false
            }
        }
    }
}
