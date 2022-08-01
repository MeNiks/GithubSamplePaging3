package com.niks.githubapp.ui.pr

import androidx.lifecycle.ViewModel
import com.niks.githubapp.di.map.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PullRequestListActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(PullRequestListViewModel::class)
    abstract fun bindsPullRequestListViewModel(vm: PullRequestListViewModel): ViewModel
}