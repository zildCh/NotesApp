package com.github.zottaa.authorization.login

import com.google.gson.annotations.SerializedName

data class ServerUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("userCategoryLinkList")
    val list: List<ServerUserCategoryLink>
)

data class ServerCategory(
    @SerializedName("id")
    val id: Long,
    @SerializedName("category")
    val name: String
)

data class ServerNote(
    @SerializedName("id")
    val id: Long,
    @SerializedName("header")
    val title: String,
    @SerializedName("note")
    val text: String,
    @SerializedName("date")
    val updateTime: Long
)

data class ServerUserCategoryLink(
    @SerializedName("category")
    val category: ServerCategory,
    @SerializedName("noteList")
    val noteList: List<ServerNote>
)