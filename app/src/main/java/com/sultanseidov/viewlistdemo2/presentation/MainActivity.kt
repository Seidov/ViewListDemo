package com.sultanseidov.viewlistdemo2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.sultanseidov.viewlistdemo2.data.local.PreferenceManager

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val showOnboarding = !preferenceManager.isOnboardingCompleted()
        
        setContent {
            ViewListApp(
                showOnboardingInitially = showOnboarding,
                finishActivity = {
                    finish()
                }
            )
        }
    }
}
