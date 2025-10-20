package com.example.raceme.profile.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raceme.profile.data.UserProfile
import com.example.raceme.profile.data.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val loading: Boolean = true,
    val profile: UserProfile = UserProfile(),
    val error: String? = null,
    val saveSuccess: Boolean = false
)

class UserProfileViewModel(
    private val repository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun load(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null, saveSuccess = false) }
            runCatching { repository.getUserProfile(userId) }
                .onSuccess { profile ->
                    _uiState.update { it.copy(loading = false, profile = profile) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(loading = false, error = e.message ?: "Failed to load profile.") }
                }
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(profile = it.profile.copy(name = name), saveSuccess = false) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(profile = it.profile.copy(email = email), saveSuccess = false) }
    }

    fun onBioChanged(bio: String) {
        _uiState.update { it.copy(profile = it.profile.copy(bio = bio), saveSuccess = false) }
    }

    fun onPhotoChanged(uri: Uri?) {
        _uiState.update { it.copy(profile = it.profile.copy(photoUri = uri?.toString()), saveSuccess = false) }
    }

    fun save() {
        viewModelScope.launch {
            val current = _uiState.value.profile
            _uiState.update { it.copy(loading = true, error = null) }
            runCatching { repository.saveUserProfile(current) }
                .onSuccess {
                    _uiState.update { it.copy(loading = false, saveSuccess = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(loading = false, error = e.message ?: "Failed to save profile.") }
                }
        }
    }
}
