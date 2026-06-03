package com.sultanseidov.viewlistdemo2.presentation.screens.onboarding

data class OnboardingState(
    val currentStep: Int = 0,
    val selectedGenreIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val isOnboardingComplete: Boolean = false,
)

sealed class OnboardingEvent {
    data object NextStep : OnboardingEvent()
    data object PrevStep : OnboardingEvent()
    data class ToggleGenre(val id: Int) : OnboardingEvent()
    data object CompleteOnboarding : OnboardingEvent()
}
