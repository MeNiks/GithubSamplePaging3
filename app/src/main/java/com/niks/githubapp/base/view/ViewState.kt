package com.niks.githubapp.base.view

import androidx.annotation.DrawableRes

sealed class ViewState {
    object LOADING : ViewState()
    object COMPLETED : ViewState()
    data class Info(
        @DrawableRes
        val image: Int,
        @DrawableRes
        val title: Int,
        @DrawableRes
        val message: Int,
        @DrawableRes
        val actionTitle: Int? = null
    ) : ViewState()
}