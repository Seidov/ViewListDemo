package com.sultanseidov.viewlistdemo2.presentation.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.viewlistdemo2.data.local.PreferenceManager
import com.sultanseidov.viewlistdemo2.domain.usecase.pin.SeedInitialOnboardingGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val seedInitialOnboardingGenresUseCase: SeedInitialOnboardingGenresUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingState())
    val uiState: StateFlow<OnboardingState> = _uiState.asStateFlow()

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.NextStep -> {
                _uiState.update { state ->
                    if (state.currentStep < 2) {
                        state.copy(currentStep = state.currentStep + 1)
                    } else {
                        state
                    }
                }
            }

            is OnboardingEvent.PrevStep -> {
                _uiState.update { state ->
                    if (state.currentStep > 0) {
                        state.copy(currentStep = state.currentStep - 1)
                    } else {
                        state
                    }
                }
            }

            is OnboardingEvent.ToggleGenre -> {
                _uiState.update { state ->
                    val newSelection = if (state.selectedGenreIds.contains(event.id)) {
                        state.selectedGenreIds - event.id
                    } else {
                        state.selectedGenreIds + event.id
                    }
                    state.copy(selectedGenreIds = newSelection)
                }
            }

            is OnboardingEvent.CompleteOnboarding -> {
                completeOnboarding()
            }
        }
    }

    private fun completeOnboarding() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // invoke operatörü sayesinde doğrudan UseCase'i fonksiyon gibi çağırıyoruz
                seedInitialOnboardingGenresUseCase(
                    selectedGenreIds = _uiState.value.selectedGenreIds.toList()
                )

                // Tercih durumunu kaydet
                preferenceManager.setOnboardingCompleted(true)

                _uiState.update { it.copy(isLoading = false, isOnboardingComplete = true) }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
