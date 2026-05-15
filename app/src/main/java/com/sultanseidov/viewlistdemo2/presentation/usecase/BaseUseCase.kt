package com.sultanseidov.viewlistdemo2.presentation.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}