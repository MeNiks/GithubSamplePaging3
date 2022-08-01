package com.niks.githubapp.ui.pr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.niks.githubapp.R
import com.niks.githubapp.base.LoggingTag
import com.niks.githubapp.base.ViewModelFactory
import com.niks.githubapp.base.logInfo
import com.niks.githubapp.base.view.ViewState
import com.niks.githubapp.databinding.ActivityPullRequestListBinding
import com.niks.githubapp.ext.isConnectedToInternet
import com.niks.githubapp.ext.launch
import com.niks.githubapp.ext.onClick
import com.niks.githubapp.network.vo.PullRequestVo
import com.niks.githubapp.recyclerview.ItemPresenter
import com.niks.githubapp.recyclerview.MarginDecorator
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import com.niks.githubapp.recyclerview.RecyclerViewPagingDataAdapter
import com.niks.githubapp.recyclerview.RecyclerViewRowPresenterManager
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class PullRequestListActivity : AppCompatActivity() {

    private var _binding: ActivityPullRequestListBinding? = null
    private val binding: ActivityPullRequestListBinding get() = _binding!!
    private lateinit var adapter: RecyclerViewPagingDataAdapter<String, RecyclerViewItemVo<String>>

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: PullRequestListViewModel by lazy {
        ViewModelProvider(this, factory)[PullRequestListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityPullRequestListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeRecyclerview()
        setupRetry()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initializeRecyclerview() {
        binding.pullRequestRv.layoutManager = LinearLayoutManager(this)
        binding.pullRequestRv.addItemDecoration(
            MarginDecorator(
                margin = resources.getDimension(R.dimen.margin_4).toInt()
            )
        )
        val presenterManager = RecyclerViewRowPresenterManager<String, RecyclerViewItemVo<String>>()
        presenterManager.addViewPresenter(
            presenter = ItemPresenter(layoutId = R.layout.pull_request_item) { item ->
                item is PullRequestVo
            }
        )
        adapter = RecyclerViewPagingDataAdapter(presenterManager)
        binding.pullRequestRv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = RetryLoadStateAdapter {
                adapter.retry()
            },
            footer = RetryLoadStateAdapter {
                adapter.retry()
            }
        )

        launch(Lifecycle.State.STARTED) {
            viewModel.getClosedPullRequestFlow(
                orgName = intent.extras?.getString(ORG_NAME) ?: "",
                repoName = intent.extras?.getString(REPO_NAME_ARG) ?: ""
            ).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        launch(Lifecycle.State.STARTED) {
            adapter
                .loadStateFlow
                .map { combinedLoadStates ->
                    viewModel.createPullRequestViewState(
                        isConnectedToInternet(),
                        sourceRefreshLoadState = combinedLoadStates.source.refresh,
                        appendLoadState = combinedLoadStates.append,
                        count = adapter.itemCount
                    )
                }
                .collectLatest { viewState ->
                    showViewState(viewState)
                }
        }
    }

    private fun setupRetry() {
        launch(Lifecycle.State.STARTED) {
            binding.infoView.infoActionBtn
                .onClick()
                .collectLatest {
                    adapter.retry()
                }
        }
    }

    private fun showViewState(viewState: ViewState) {
        logInfo(
            logTag = LoggingTag.PULL_REQUEST.getName(),
            message = "Main View State : $viewState"
        )
        binding.pullRequestRv.isVisible = viewState == ViewState.COMPLETED
        binding.infoView.root.isVisible = viewState is ViewState.Info
        if (viewState is ViewState.Info) {
            binding.infoView.infoIv.setImageResource(viewState.image)
            binding.infoView.infoTitleTv.setText(viewState.title)
            binding.infoView.infoDetailTv.setText(viewState.message)
            val actionTitle = viewState.actionTitle
            if (actionTitle == null) {
                binding.infoView.infoActionBtn.isVisible = false
            } else {
                binding.infoView.infoActionBtn.setText(viewState.actionTitle)
                binding.infoView.infoActionBtn.isVisible = true
            }
        }
        binding.progressBar.isVisible = viewState == ViewState.LOADING
    }




    companion object {
        const val ORG_NAME = "org_name"
        const val REPO_NAME_ARG = "repo_name"
    }
}
