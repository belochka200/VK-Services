package com.example.vkservices.ui.screens

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
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
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                mainScreenViewModel.fetchData()
            }
        }
        mainScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is MainScreenUiState.Content -> showContent(
                    listOfServices = uiState.listOfServices,
                    errorMessage = uiState.errorMessage
                )

                MainScreenUiState.Loading -> showLoading()
            }
        }
        checkUserInternet()
    }

    private fun checkUserInternet() {
        val manager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities == null)
            Snackbar.make(
                requireView(),
                R.string.connection_error,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.retry) {
                mainScreenViewModel.fetchData()
            }.show()
    }

    private fun showContent(listOfServices: List<Service>, @StringRes errorMessage: Int?) {
        if (errorMessage != null)
            Snackbar.make(requireView(), R.string.error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) {
                    mainScreenViewModel.fetchData()
                }.show()
        checkUserInternet()
        binding.apply {
            swipeRefreshLayout.isRefreshing = false
            progressIndicator.isVisible = false
            rvServicesOfVk.adapter = ServicesVkAdapter(listOfServices) { service ->
                showInfoAboutService(service) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(service.serviceUrl))
                    startActivity(intent)
                }
            }
        }
    }

    private fun showInfoAboutService(service: Service, clickListener: () -> Unit) {
        val modalBottomSheet = AboutService(service, clickListener)
        modalBottomSheet.show(requireActivity().supportFragmentManager, AboutService.TAG)
    }

    private fun showLoading() {
        binding.apply {
            progressIndicator.isVisible = true
            swipeRefreshLayout.isRefreshing = false
        }
    }
}