package com.sultanseidov.viewlistdemo2.domain.usecase.genre

import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.data.model.dto.genre.GenresMovieModel
import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class GetGenreMoviesUseCase@Inject constructor(
    private val repository: INewRepository
){
    suspend operator fun invoke(): Flow<ResourceState<List<GenresMovieModel>>> {
        return repository.getMovieGenres()
    }
}
