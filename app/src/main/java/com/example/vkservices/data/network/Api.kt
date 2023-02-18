package com.example.vkservices.data.network

import com.example.vkservices.data.models.Service
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

const val BASE_URL: String = "https://mobile-olympiad-trajectory.hb.bizmrg.com"

interface Api {
    suspend fun getAllServices(): List<Service>
}

class ApiImpl : Api {
    override suspend fun getAllServices(): List<Service> {
        val response = URL("$BASE_URL/semi-final-data.json").readText()
        return Json.decodeFromString(response.substring(startIndex = 15, endIndex = response.length - 2))
    }
}
