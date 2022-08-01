package com.niks.githubapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.niks.githubapp.di.scope.PerActivity
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@PerActivity
open class ViewModelFactory
@Inject
constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = creators[modelClass]?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            viewModelProvider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}