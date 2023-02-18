package com.example.vkservices.domain.models

import com.example.vkservices.ui.models.Service as UiService

data class Service(
    val name: String,
    val description: String,
    val iconUrl: String,
    val serviceUrl: String,
)

fun List<Service>.asUi(): List<UiService> {
    return map {
        UiService(
            name = it.name,
            description = it.description,
            iconUrl = it.iconUrl,
            serviceUrl = it.serviceUrl
        )
    }
}