package com.alex_podolian.stackexchangetestapp.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val id: Int,
    @SerializedName("display_name")
    val name: String,
    @SerializedName("badge_counts")
    val badges: Badges,
    @SerializedName("creation_date")
    val creationDate: Long,
    @SerializedName("location")
    val location: String,
    @SerializedName("profile_image")
    val profileImageUrl: String,
    @SerializedName("reputation")
    val reputation: Int
)

data class Badges(
    @SerializedName("bronze")
    val bronze: Int,
    @SerializedName("gold")
    val gold: Int,
    @SerializedName("silver")
    val silver: Int
)

data class Users(
    @SerializedName("items")
    val items: List<User>,
)