package com.example.vkservices.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.vkservices.domain.models.Service as DomainService

@Serializable
data class Service(
    val name: String,
    val description: String,
    @SerialName("icon_url") val iconUrl: String,
    @SerialName("service_url") val serviceUrl: String,
)

fun List<Service>.asDomain(): List<DomainService> {
    return map {
        DomainService(
            name = it.name,
            description = it.description,
            iconUrl = it.iconUrl,
            serviceUrl = it.serviceUrl
        )
    }
}