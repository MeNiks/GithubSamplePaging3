package com.niks.githubapp.di.module

import com.niks.githubapp.di.scope.PerActivity
import com.niks.githubapp.ui.pr.PullRequestListActivity
import com.niks.githubapp.ui.pr.PullRequestListActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [PullRequestListActivityModule::class])
    @PerActivity
    internal abstract fun bindLoginActivity(): PullRequestListActivity
}