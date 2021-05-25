package a.com.creekcards.models

import com.google.gson.annotations.SerializedName

class CardModel {
    @SerializedName("data")
    val dataList: List<CardItems> = emptyList()
}