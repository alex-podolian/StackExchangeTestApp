package com.alex_podolian.stackexchangetestapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TopTag(
    @SerializedName("tag_name")
    val tagName: String
) : Serializable

data class TopTags(
    @SerializedName("items")
    val items: List<TopTag>,
)