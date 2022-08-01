package com.niks.githubapp.network.vo

import com.google.gson.annotations.SerializedName
import com.niks.githubapp.ext.getReadableDate
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import java.util.*

data class PullRequestVo(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val created_at: Date,
    @SerializedName("closed_at")
    val closed_at: Date? = null,
    @SerializedName("user")
    val user: GithubUserVo? = null
) : RecyclerViewItemVo<String> {
    override fun getItemId(): String = id

    fun getReadableCreatedDate(): String {
        return getReadableDate(created_at)
    }

    fun getReadableClosedAt(): String {
        return getReadableDate(closed_at)
    }
}