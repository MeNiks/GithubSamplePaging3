package com.niks.githubapp.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.niks.githubapp.R
import com.niks.githubapp.base.ViewModelFactory
import com.niks.githubapp.databinding.ActivityLandingScreenBinding
import com.niks.githubapp.ext.launch
import com.niks.githubapp.ext.onClick
import com.niks.githubapp.ext.showToast
import com.niks.githubapp.ui.pr.PullRequestListActivity
import com.niks.githubapp.ui.pr.PullRequestListViewModel
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class LandingScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingScreenBinding

    private val viewModel: LandingScreenActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewClosedPrClickListener()
    }

    private fun setupViewClosedPrClickListener() {
        launch(Lifecycle.State.STARTED) {
            binding
                .viewClosedPRBtn
                .onClick()
                .collectLatest {
                    startShowPullRequestListActivity()
                }
        }
    }

    private fun startShowPullRequestListActivity() {
        val orgName = binding.orgNameEt.text?.toString()?.trim() ?: ""
        val repoName = binding.repoNameEt.text?.toString()?.trim() ?: ""
        val errorMessage = viewModel.validateAndGetErrorMessage(
            orgName = orgName,
            repoNameString = repoName
        )
        if (errorMessage == null) {
            startActivity(
                Intent(this, PullRequestListActivity::class.java).apply {
                    putExtra(PullRequestListActivity.ORG_NAME, orgName)
                    putExtra(PullRequestListActivity.REPO_NAME_ARG, repoName)
                }
            )
        } else {
            showToast(getString(errorMessage))
        }
    }
}