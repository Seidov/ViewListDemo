package com.sultanseidov.viewlistdemo2.data.repository

import androidx.paging.*
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.data.remote.ITmdbApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class Repository2 @Inject constructor(
    private val iTmdbApi: ITmdbApi,
    private val appDatabase: AppDatabase
)