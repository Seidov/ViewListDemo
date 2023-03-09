package com.sultanseidov.viewlistdemo2.data.entity.base

sealed class ResourceState<out T> {
    object Idle : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
    data class Success<T>(val data: T?) : ResourceState<T>()
    data class Error(val error: Throwable) : ResourceState<Nothing>()
}
