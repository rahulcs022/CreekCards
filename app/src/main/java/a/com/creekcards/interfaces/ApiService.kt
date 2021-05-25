package a.com.creekcards.interfaces

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("fjaqJ")
    suspend fun getCardData(): String
}