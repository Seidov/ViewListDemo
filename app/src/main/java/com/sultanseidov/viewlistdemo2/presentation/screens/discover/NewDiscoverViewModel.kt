package com.sultanseidov.viewlistdemo2.presentation.screens.discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.entity.DiscoveryMediaEntity
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import com.sultanseidov.viewlistdemo2.domain.usecase.pin.WatchClassificationPipelineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DiscoveryTabItem(
    val tag: String,
    val title: String,
    val mediaType: String,
    val genres: String
)

@ExperimentalPagingApi
@HiltViewModel
class NewDiscoverViewModel @Inject constructor(
    private val pinDao: PinDao,
    private val repository: IMovieRepository,
    private val watchClassificationPipelineUseCase: WatchClassificationPipelineUseCase
) : ViewModel() {

    // Real-time dynamic tabs from DB + Static ones
    val tabs: StateFlow<List<DiscoveryTabItem>> = pinDao.getActivePinsFlow().map { pins ->
        val staticTabs = listOf(
            DiscoveryTabItem("MOVIES", "Movies", "MOVIE", ""),
            DiscoveryTabItem("TV_SHOWS", "TV Shows", "TV", "")
        )
        val dynamicTabs = pins.map { pin ->
            DiscoveryTabItem(
                tag = "PIN_${pin.pinId}",
                title = pin.title,
                mediaType = "MOVIE", // Pins are currently movie-centric in classification
                genres = pin.dominantGenres.joinToString(",")
            )
        }
        staticTabs + dynamicTabs
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<DiscoveryMediaEntity>> = combine(tabs, _selectedTabIndex) { tabList, index ->
        if (tabList.isEmpty()) null else tabList.getOrNull(index)
    }.filterNotNull()
     .flatMapLatest { tab ->
        repository.getDiscoveryFlowByTag(tab.tag, tab.mediaType, tab.genres)
    }.cachedIn(viewModelScope)

    private val _isClassifying = MutableStateFlow(false)
    val isClassifying: StateFlow<Boolean> = _isClassifying.asStateFlow()

    fun onTabChanged(index: Int) {
        _selectedTabIndex.value = index
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            is DiscoverEvent.GetHome -> { }
            is DiscoverEvent.OnListClicked -> {
                viewModelScope.launch {
                    _isClassifying.value = true
                    try {
                        watchClassificationPipelineUseCase(event.movieId)
                    } catch (e: Exception) {
                        Log.e("NewDiscoverViewModel", "Classification error: ${e.message}")
                    } finally {
                        _isClassifying.value = false
                    }
                }
            }
        }
    }
}
