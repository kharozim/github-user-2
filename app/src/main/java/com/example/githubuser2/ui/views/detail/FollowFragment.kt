package com.example.githubuser2.ui.views.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.githubuser2.R
import com.example.githubuser2.databinding.FragmentFollowBinding
import com.example.githubuser2.model.responses.UserItem

class FollowFragment : Fragment() {

    companion object {
        const val IS_FOLLOWER = "IS_FOLLOWER"
        fun onSaveInstance(isFollower: Boolean): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putBoolean(IS_FOLLOWER, isFollower)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentFollowBinding
    private val adapter by lazy { FollowAdapter() }

    private var isFollower = false


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FOLLOWER, isFollower)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        isFollower =
            savedInstanceState?.getBoolean(IS_FOLLOWER, false)
                ?: (arguments?.getBoolean(IS_FOLLOWER, false) ?: false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        if (isFollower) {
            getDataFolower()
            setObserverFollower()
            binding.swipeRefresh.setOnRefreshListener {
                getDataFolower()
            }
        } else {
            getDataFollowing()
            setObserverFollowing()
            binding.swipeRefresh.setOnRefreshListener {
                getDataFollowing()
            }
        }
    }


    private fun getDataFolower() {
        adapter.resetData()
        activity().viewModel.getUserFollowers(username = activity().username)
    }

    private fun setObserverFollower() {
        followerObserver()
        loadingObserver()
    }

    private fun followerObserver() {
        activity().viewModel.followers.observe(viewLifecycleOwner, { result ->
            setData(result ?: emptyList())
        })
    }


    private fun getDataFollowing() {
        adapter.resetData()
        activity().viewModel.getUserFollowing(username = activity().username)
    }

    private fun setObserverFollowing() {
        followingObserver()
        loadingObserver()

    }

    private fun loadingObserver() {
        activity().viewModel.isLoading.observe(viewLifecycleOwner, { result ->
            if (result) {
                activity().loading.showLoading(getString(R.string.load_data))
                binding.swipeRefresh.isRefreshing = false
            } else {
                activity().loading.hideLoading()
                binding.swipeRefresh.isRefreshing = false
            }
        })
    }

    private fun followingObserver() {
        activity().viewModel.following.observe(viewLifecycleOwner, { result ->
            setData(result ?: emptyList())
        })
    }

    private fun setData(result: List<UserItem>) {
        adapter.items = result
        binding.followRvListUser.adapter = adapter
        binding.followRvListUser.scheduleLayoutAnimation()
    }


    private fun activity() = requireActivity() as DetailActivity
}