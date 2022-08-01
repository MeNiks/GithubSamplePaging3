package com.niks.githubapp.ui.pr

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.niks.githubapp.base.LoggingTag
import com.niks.githubapp.base.logError
import com.niks.githubapp.base.logInfo
import com.niks.githubapp.network.vo.PullRequestState
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import com.niks.githubapp.ui.pr.ds.PullRequestDataSource

class PullRequestPagingSource(
    private val pullRequestDataSource: PullRequestDataSource,
    private val orgName: String,
    private val repoName: String
) : PagingSource<Long, RecyclerViewItemVo<String>>() {

    override fun getRefreshKey(state: PagingState<Long, RecyclerViewItemVo<String>>): Long? {
        val refreshPageKey = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        logInfo(
            logTag = LoggingTag.PULL_REQUEST.getName(),
            message = "getRefreshKey" +
                    "\nanchorPosition : ${state.anchorPosition}" +
                    "\nrefreshPageKey : $refreshPageKey" +
                    "\npages.size : ${state.pages.size}"
        )
        return refreshPageKey
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, RecyclerViewItemVo<String>> {
        return try {
            var pageNumber = params.key
            pageNumber = if (pageNumber == null || pageNumber <= 0)
                INITIAL_PAGE_NUMBER
            else pageNumber
            logInfo(
                logTag = LoggingTag.PULL_REQUEST.getName(),
                "load" +
                        "\nkey : ${params.key}" +
                        "\nRequesting Page: $pageNumber" +
                        "\nloadSize : ${params.loadSize}" +
                        "\nplaceholdersEnabled : ${params.placeholdersEnabled}"
            )

            val list = pullRequestDataSource.getClosedPullRequest(
                orgName = orgName,
                repoName = repoName,
                page = pageNumber,
                limit = params.loadSize,
                state = PullRequestState.CLOSED.name.lowercase()
            )

            LoadResult.Page(
                list,
                prevKey = if (pageNumber == INITIAL_PAGE_NUMBER) null else pageNumber - 1,
                nextKey = if (list.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            logError(LoggingTag.PULL_REQUEST.getName(), e)
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1L
    }
}