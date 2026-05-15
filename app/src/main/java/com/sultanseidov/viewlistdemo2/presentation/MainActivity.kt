package com.sultanseidov.viewlistdemo2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // This app draws behind the system bars, so we want to handle fitting system windows
            //WindowCompat.setDecorFitsSystemWindows(window, false)
            setContent {
                ViewListApp {
                    finish()
                }
            }

        }
    }
}