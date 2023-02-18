package com.example.vkservices.domain.usecases

import com.example.vkservices.data.models.asDomain
import com.example.vkservices.data.network.ApiImpl
import com.example.vkservices.domain.models.Service

interface LoadAllServicesUseCase {
    suspend fun loadAllServices(): List<Service>
}

class LoadAllServicesUseCaseImpl() : LoadAllServicesUseCase {
    override suspend fun loadAllServices(): List<Service> {
        return ApiImpl().getAllServices().asDomain()
    }
}
