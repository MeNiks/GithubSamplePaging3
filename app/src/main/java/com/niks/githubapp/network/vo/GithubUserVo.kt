package com.niks.githubapp.network.vo

import com.google.gson.annotations.SerializedName

data class GithubUserVo(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)