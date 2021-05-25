package a.com.creekcards.models

import com.google.gson.annotations.SerializedName

data class CardItems(
        @SerializedName("id")
        val id: String,
        @SerializedName("text")
        val title: String)