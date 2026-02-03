package com.sultanseidov.viewlistdemo2.domain.usecase.discover

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import com.sultanseidov.viewlistdemo2.presentation.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetDiscoverMoviesUseCase@Inject constructor(
    private val repository: INewRepository
): BaseUseCase<Unit, Flow<PagingData<MovieModel>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<MovieModel>> {
        return repository.getAllDiscoverMovies("")
    }
}