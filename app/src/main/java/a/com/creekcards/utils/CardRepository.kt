package a.com.creekcards.utils

class CardRepository (private val apiHelper: ApiHelper) {
    suspend fun getCardData() = apiHelper.getCardData()
}