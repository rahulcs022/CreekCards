package a.com.creekcards.view_models

import a.com.creekcards.utils.CardRepository
import a.com.creekcards.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {
    fun getData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = cardRepository.getCardData()
            emit(Resource.success(response.toString().replace("/", "").replace("\n", "")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}