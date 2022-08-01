package com.niks.githubapp.ui.pr

import androidx.paging.PagingSource
import com.niks.githubapp.MockDataUtil
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import com.niks.githubapp.ui.pr.ds.PullRequestDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class PullRequestPagingSourceTest {

    private val pullRequestDataSource: PullRequestDataSource = mockk(relaxed = true)

    @Test
    fun `Verify Pull Request Empty List is working correctly`() = runTest {

        val orgName = "ABC"
        val repoName = "Dev"

        coEvery {
            pullRequestDataSource.getClosedPullRequest(
                orgName = orgName,
                repoName = repoName,
                page = any(),
                state = any(),
                limit = any()
            )
        } returns arrayListOf()
        val pagingSource = PullRequestPagingSource(
            pullRequestDataSource = pullRequestDataSource,
            orgName = orgName,
            repoName = repoName
        )

        val result: PagingSource.LoadResult<Long, RecyclerViewItemVo<String>> = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        assert(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assert(result.data.isEmpty())
    }

    @Test
    fun `Verify Pull Request Non Empty List is working correctly`() = runTest {

        val orgName = "ABC"
        val repoName = "Dev"

        coEvery {
            pullRequestDataSource.getClosedPullRequest(
                orgName = orgName,
                repoName = repoName,
                page = any(),
                state = any(),
                limit = any()
            )
        } returns arrayListOf(
            MockDataUtil.getPullRequestVo("1", "Pr 1"),
            MockDataUtil.getPullRequestVo("2", "Pr 2")
        )

        val pagingSource = PullRequestPagingSource(
            pullRequestDataSource = pullRequestDataSource,
            orgName = orgName,
            repoName = repoName
        )

        val result: PagingSource.LoadResult<Long, RecyclerViewItemVo<String>> = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        assert(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assert(result.data.size == 2)
    }

    @Test
    fun `Verify Pull Request Error case is working correctly`() = runTest {

        val orgName = "ABC"
        val repoName = "Dev"

        coEvery {
            pullRequestDataSource.getClosedPullRequest(
                orgName = orgName,
                repoName = repoName,
                page = any(),
                state = any(),
                limit = any()
            )
        } throws IllegalStateException("Internet not present")

        val pagingSource = PullRequestPagingSource(
            pullRequestDataSource = pullRequestDataSource,
            orgName = orgName,
            repoName = repoName
        )

        val result: PagingSource.LoadResult<Long, RecyclerViewItemVo<String>> = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        assert(result is PagingSource.LoadResult.Error)
    }
}