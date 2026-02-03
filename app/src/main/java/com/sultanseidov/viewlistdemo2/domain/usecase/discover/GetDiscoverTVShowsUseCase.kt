package com.sultanseidov.viewlistdemo2.domain.usecase.discover

import androidx.paging.PagingData
import com.sultanseidov.viewlistdemo2.data.model.base.ResourceState
import com.sultanseidov.viewlistdemo2.domain.model.TvShowModel
import com.sultanseidov.viewlistdemo2.domain.repository.INewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

data class GetDiscoverTVShowsUseCase@Inject constructor(
    private val repository: INewRepository
){

    fun executeGetTVShows(search: String) : Flow<ResourceState<Flow<PagingData<TvShowModel>>>> = flow {
        try {
            emit(ResourceState.Loading())
            emit(ResourceState.Success(repository.getAllDiscoverTvShows(search)))
        } catch (e: HttpException) {
            emit(ResourceState.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResourceState.Error( message = "Could not reach internet"))
        }
    }

}
