package com.sultanseidov.viewlistdemo2.domain.usecase.discover

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import com.sultanseidov.viewlistdemo2.presentation.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetDiscoverTVShowsUseCase @Inject constructor(
    private val repository: INewRepository
) : BaseUseCase<Unit, Flow<PagingData<TvShowModel>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<TvShowModel>> {
        return repository.getAllDiscoverTvShows("")
    }
}
