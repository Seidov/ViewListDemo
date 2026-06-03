package com.sultanseidov.viewlistdemo2.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("viewlist_prefs", Context.MODE_PRIVATE)

    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean("is_onboarding_completed", false)
    }

    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit {
            putBoolean("is_onboarding_completed", completed)
        }
    }
}
