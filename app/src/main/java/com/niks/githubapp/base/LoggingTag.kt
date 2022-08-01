package com.niks.githubapp.base

import timber.log.Timber

enum class LoggingTag {
    PULL_REQUEST;

    fun getName(): String {
        return name
    }
}

fun logInfo(logTag: String, message: String) {
    Timber.tag(logTag).i("%s", message)
}

fun logError(logTag: String, e: Throwable, message: String = "") {
    Timber.tag(logTag).e(message, e)
}