package com.niks.githubapp.ui.pr

import androidx.paging.LoadState
import com.niks.githubapp.R
import com.niks.githubapp.base.view.ViewState
import com.niks.githubapp.ui.pr.ds.PullRequestDataSource
import io.mockk.mockk
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException


class PullRequestListViewModelTest {

    private val pullRequestDataSource: PullRequestDataSource = mockk(relaxed = true)

    @Test
    fun `Verify loading state is created`() {
        val mainSource = LoadState.Loading
        val isConnected = true
        val itemsLoaded = 0

        val viewModel = PullRequestListViewModel(pullRequestDataSource = pullRequestDataSource)
        val result = viewModel.createPullRequestViewState(
            isConnected = isConnected,
            sourceRefreshLoadState = mainSource,
            appendLoadState = LoadState.NotLoading(false),
            count = itemsLoaded
        )

        Assert.assertEquals(
            result,
            ViewState.LOADING
        )
    }

    @Test
    fun `Verify 404 state is created`() {
        val errorResponse = Response.error<String>(404, ResponseBody.create(null, ""))
        val mainSource = LoadState.Error(HttpException(errorResponse))
        val itemsLoaded = 0
        val isConnected = true

        val viewModel = PullRequestListViewModel(pullRequestDataSource = pullRequestDataSource)
        val result = viewModel.createPullRequestViewState(
            isConnected = isConnected,
            sourceRefreshLoadState = mainSource,
            appendLoadState = LoadState.NotLoading(false),
            count = itemsLoaded
        )

        Assert.assertEquals(
            result,
            ViewState.Info(
                image = R.drawable.ic_404,
                title = R.string.repo_not_found,
                message = R.string.repo_not_found_desc,
                actionTitle = R.string.retry
            )
        )
    }

    @Test
    fun `Verify failed state is created`() {
        val errorResponse = Response.error<String>(401, ResponseBody.create(null, ""))
        val mainSource = LoadState.Error(HttpException(errorResponse))
        val itemsLoaded = 0
        val isConnected = true

        val viewModel = PullRequestListViewModel(pullRequestDataSource = pullRequestDataSource)
        val result = viewModel.createPullRequestViewState(
            isConnected = isConnected,
            sourceRefreshLoadState = mainSource,
            appendLoadState = LoadState.NotLoading(false),
            count = itemsLoaded
        )

        Assert.assertEquals(
            result,
            ViewState.Info(
                image = R.drawable.ic_no_data_available,
                title = R.string.unable_to_load_pr_list,
                message = R.string.unable_to_load_pr_list_desc,
                actionTitle = R.string.retry
            )
        )
    }

    @Test
    fun `Verify no internet state is created`() {
        val mainSource =
            LoadState.Error(UnknownHostException("Unable to resolve host \"api.github.com\": No address associated with hostname"))
        val itemsLoaded = 0
        val isConnected = false

        val viewModel = PullRequestListViewModel(pullRequestDataSource = pullRequestDataSource)
        val result = viewModel.createPullRequestViewState(
            isConnected = isConnected,
            sourceRefreshLoadState = mainSource,
            appendLoadState = LoadState.NotLoading(false),
            count = itemsLoaded
        )

        Assert.assertEquals(
            result,
            ViewState.Info(
                image = R.drawable.ic_no_internet,
                title = R.string.no_internet_available,
                message = R.string.no_internet_available_desc,
                actionTitle = R.string.retry
            )
        )
    }


    @Test
    fun `Verify empty state is created`() {
        val mainSource = LoadState.NotLoading(false)
        val itemsLoaded = 0
        val isConnected = true

        val viewModel = PullRequestListViewModel(pullRequestDataSource = pullRequestDataSource)
        val result = viewModel.createPullRequestViewState(
            isConnected = isConnected,
            sourceRefreshLoadState = mainSource,
            appendLoadState = LoadState.NotLoading(false),
            count = itemsLoaded
        )

        Assert.assertEquals(
            result,
            ViewState.Info(
                image = R.drawable.ic_no_data_available,
                title = R.string.pr_empty_list,
                message = R.string.pr_empty_list_desc
            )
        )
    }

    @Test
    fun `Verify completed state is created`() {
        val mainSource = LoadState.NotLoading(true)
        val itemsLoaded = 10

        val viewModel = PullRequestListViewModel(pullRequestDataSource = pullRequestDataSource)
        val result = viewModel.createPullRequestViewState(
            isConnected = true,
            sourceRefreshLoadState = mainSource,
            appendLoadState = LoadState.NotLoading(false),
            count = itemsLoaded
        )

        Assert.assertEquals(result, ViewState.COMPLETED)
    }
}