package com.sultanseidov.viewlistdemo2.data.model.base

sealed class ResourceState<T>(val data: T? = null, val message:String? = null) {
    class Success<T>(data: T) : ResourceState<T>(data = data)
    class Error<T>(message: String, data:T? = null) : ResourceState<T>(data = data,message=message)
    class Loading<T>(data:T?= null) : ResourceState<T>(data=data)
}
