package com.example.vkservices.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vkservices.R
import com.example.vkservices.domain.models.asUi
import com.example.vkservices.domain.usecases.LoadAllServicesUseCaseImpl
import com.example.vkservices.ui.uistates.MainScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState: MutableLiveData<MainScreenUiState> = MutableLiveData(
        MainScreenUiState.Content(
            listOfServices = emptyList()
        )
    )
    val uiState: LiveData<MainScreenUiState> = _uiState

    private var job: Job? = null

    init {
        fetchData()
    }

    fun fetchData() {
        job?.cancel()
        _uiState.value = MainScreenUiState.Loading
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val services = LoadAllServicesUseCaseImpl().loadAllServices()
                _uiState.postValue(
                    MainScreenUiState.Content(
                        listOfServices = services.asUi()
                    )
                )
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
                Log.e("Exception", e.message.toString())
                _uiState.postValue(
                    MainScreenUiState.Content(
                        errorMessage = R.string.error,
                        listOfServices = emptyList()
                    )
                )
            }
        }
    }
}