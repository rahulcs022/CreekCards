package a.com.creekcards.utils

import a.com.creekcards.interfaces.ApiService

class ApiHelper (private val apiService: ApiService) {
    suspend fun getCardData() = apiService.getCardData()
}