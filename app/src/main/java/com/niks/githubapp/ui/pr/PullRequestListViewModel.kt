package com.niks.githubapp.ui.pr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.niks.githubapp.R
import com.niks.githubapp.base.LoggingTag
import com.niks.githubapp.base.logInfo
import com.niks.githubapp.base.view.ViewState
import com.niks.githubapp.di.qualifier.Repository
import com.niks.githubapp.ext.isConnectedToInternet
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import com.niks.githubapp.ui.pr.ds.PullRequestDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class PullRequestListViewModel
@Inject
constructor(
    @Repository
    private val pullRequestDataSource: PullRequestDataSource,
) : ViewModel() {

    fun getClosedPullRequestFlow(
        orgName: String,
        repoName: String
    ): Flow<PagingData<RecyclerViewItemVo<String>>> {
        return pullRequestDataSource
            .getClosedPullRequestFlow(
                orgName,
                repoName
            )
            .cachedIn(viewModelScope)
    }

    fun createPullRequestViewState(
        isConnected: Boolean,
        sourceRefreshLoadState: LoadState,
        appendLoadState: LoadState,
        count: Int
    ): ViewState {
        //Show loading,error or empty state only if data is not loaded on ui ie count ==0
        if (count == 0) {
            return when (sourceRefreshLoadState) {
                LoadState.Loading -> {
                    ViewState.LOADING
                }
                is LoadState.Error -> {
                    val error = sourceRefreshLoadState.error
                    if (isConnected) {
                        if (error is HttpException && error.code() == 404)
                            ViewState.Info(
                                image = R.drawable.ic_404,
                                title = R.string.repo_not_found,
                                message = R.string.repo_not_found_desc,
                                actionTitle = R.string.retry
                            )
                        else
                            ViewState.Info(
                                image = R.drawable.ic_no_data_available,
                                title = R.string.unable_to_load_pr_list,
                                message = R.string.unable_to_load_pr_list_desc,
                                actionTitle = R.string.retry
                            )
                    } else
                        ViewState.Info(
                            image = R.drawable.ic_no_internet,
                            title = R.string.no_internet_available,
                            message = R.string.no_internet_available_desc,
                            actionTitle = R.string.retry
                        )
                }
                is LoadState.NotLoading -> {
                    return ViewState.Info(
                        image = R.drawable.ic_no_data_available,
                        title = R.string.pr_empty_list,
                        message = R.string.pr_empty_list_desc
                    )

                }
            }
        } else {
            logAppendState(appendLoadState)
        }
        return ViewState.COMPLETED
    }

    private fun logAppendState(state: LoadState) {
        when (state) {
            is LoadState.Error -> {
                logInfo(LoggingTag.PULL_REQUEST.getName(), "Loading Append Item Failed")
            }
            else -> {}
        }
    }
}