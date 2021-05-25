package a.com.creekcards.view_models

import a.com.creekcards.utils.ApiHelper
import a.com.creekcards.utils.CardRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory (private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            return CardViewModel(CardRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}