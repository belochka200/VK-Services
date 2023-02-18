package com.example.vkservices.ui.screens

import android.os.Bundle
import android.view.View
import coil.load
import com.example.vkservices.R
import com.example.vkservices.databinding.BottomSheetAboutServiceBinding
import com.example.vkservices.ui.models.Service
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AboutService(private val service: Service, private val clickListener: () -> Unit) :
    BottomSheetDialogFragment(R.layout.bottom_sheet__about_service) {
    companion object {
        const val TAG = "AboutService"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = BottomSheetAboutServiceBinding.bind(view)
        binding.apply {
            with(service) {
                serviceName.text = name
                serviceDescribe.text = description
                serviceIcon.load(iconUrl) {
                    crossfade(500)
                }
                serviceIcon.contentDescription = getString(R.string.content_description, name)
                buttonInService.setOnClickListener { clickListener() }
            }
        }
    }
}