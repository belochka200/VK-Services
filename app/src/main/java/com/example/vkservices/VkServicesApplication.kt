package com.example.vkservices

import android.app.Application
import com.google.android.material.color.DynamicColors

class VkServicesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}