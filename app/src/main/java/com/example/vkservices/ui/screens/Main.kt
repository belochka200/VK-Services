package com.example.vkservices.ui.screens

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.vkservices.R
import com.example.vkservices.databinding.FragmentMainScreenBinding
import com.example.vkservices.ui.adapters.ServicesVkAdapter
import com.example.vkservices.ui.models.Service
import com.example.vkservices.ui.uistates.MainScreenUiState
import com.example.vkservices.ui.viewmodels.MainScreenViewModel
import com.google.android.material.snackbar.Snackbar

class Main : Fragment(R.layout.fragment__main_screen) {
    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    private lateinit var binding: FragmentMainScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainScreenBinding.bind(view)
        mainScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is MainScreenUiState.Content -> showContent(
                    listOfServices = uiState.listOfServices,
                    errorMessage = uiState.errorMessage
                )

                MainScreenUiState.Loading -> showLoading()
            }
        }
    }

    private fun showContent(listOfServices: List<Service>, @StringRes errorMessage: Int?) {
        if (errorMessage != null)
            Snackbar.make(requireView(), R.string.error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) {
                    mainScreenViewModel.fetchData()
                }.show()
        binding.apply {
            progressIndicator.isVisible = false
            rvServicesOfVk.adapter = ServicesVkAdapter(listOfServices)
        }
    }

    private fun showLoading() {
        binding.apply {
            progressIndicator.isVisible = true
        }
    }
}