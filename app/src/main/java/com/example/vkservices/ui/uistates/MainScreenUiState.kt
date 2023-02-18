package com.example.vkservices.ui.uistates

import androidx.annotation.StringRes
import com.example.vkservices.ui.models.Service

sealed class MainScreenUiState {
    object Loading : MainScreenUiState()
    data class Content(
        @StringRes val errorMessage: Int? = null,
        val listOfServices: List<Service>
    ) : MainScreenUiState()
}