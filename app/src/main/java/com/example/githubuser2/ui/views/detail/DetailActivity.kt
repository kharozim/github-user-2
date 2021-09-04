package com.example.githubuser2.ui.views.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import coil.load
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ActivityDetailBinding
import com.example.githubuser2.model.responses.UserItem
import com.example.githubuser2.repository.remote.RetrofitClient
import com.example.githubuser2.repository.contract.UserContractRepoImpl
import com.example.githubuser2.repository.local.AppDatabase
import com.example.githubuser2.ui.utils.LoadingUtil
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"
    }

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val service by lazy { RetrofitClient.userService }
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val dao by lazy { db.UserDao() }
    private val repository by lazy { UserContractRepoImpl(service, dao) }
    internal val viewModel by lazy {
        ViewModelProvider(
            this,
            DetailViewModelFactory(repository)
        ).get(DetailViewModel::class.java)
    }
    internal val loading by lazy { LoadingUtil(this) }

    internal lateinit var username: String
    private var followerCount = 0
    private var followingCount = 0
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getData()
        setObserver()

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.tag_username, username)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setObserver() {
        getDetailUserObserver()
        loadingObserver()
        addFavObserver()
        removeFavObserver()
        findFavObserver()
    }

    private fun findFavObserver() {
        viewModel.onFindFav.observe(this, { result ->
            isFavorite = result != null
            updateFABView()
        })
    }

    private fun removeFavObserver() {
        viewModel.onRemoveFav.observe(this, { result ->
            isFavorite = false
            updateFABView()
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        })
    }

    private fun addFavObserver() {
        viewModel.onAddFav.observe(this, { result ->
            isFavorite = true
            updateFABView()
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        })
    }

    private fun loadingObserver() {
        viewModel.isLoading.observe(this, { result ->
            if (result) {
                loading.showLoading(getString(R.string.load_data))
            } else {
                loading.hideLoading()
            }

        })
    }

    private fun getDetailUserObserver() {
        viewModel.user.observe(this, { result ->
            if (result != null) {
                setDataUser(result)
                viewModel.findUserFavorite(result.id ?: 0)
            }
        })
    }

    private fun setDataUser(data: UserItem) {
        followerCount = data.followers ?: 0
        followingCount = data.following ?: 0
        binding.run {
            layoutTop.detailEmail.isVisible = !data.email.isNullOrEmpty()
            layoutTop.detailCompany.isVisible = !data.company.isNullOrEmpty()
            layoutTop.detailLocation.isVisible = !data.location.isNullOrEmpty()

            layoutTop.detailFullName.text = data.name
            layoutTop.detailEmail.text = data.email
            layoutTop.detailCompany.text = data.company
            layoutTop.detailLocation.text = data.location
            layoutTop.detailImageUser.load(data.avatarUrl) {
                placeholder(R.drawable.ic_logo)
            }
            layoutTop.fabFavorite.setOnClickListener {
                if (!isFavorite) {
                    viewModel.addFavorite(data)
                } else {
                    viewModel.removeFavorite(data.id ?: 0)
                }

            }
        }
        setupViewPager()
    }

    private fun updateFABView() {
        if (isFavorite) {
            binding.layoutTop.fabFavorite.backgroundTintList =
                ResourcesCompat.getColorStateList(resources, R.color.secondaryColor, null)
        } else {
            binding.layoutTop.fabFavorite.backgroundTintList =
                ResourcesCompat.getColorStateList(resources, R.color.grey, null)
        }

    }

    private fun setupViewPager() {
        val vpAdapter = MyViewPagerAdapter()
        binding.run {
            detailViewPager.isUserInputEnabled = false
            detailViewPager.adapter = vpAdapter
            TabLayoutMediator(detailTabLayout, detailViewPager) { tab, position ->
                tab.text = if (position == 0) {
                    getString(R.string.text_count_follower, followerCount)
                } else {
                    getString(R.string.text_count_following, followingCount)
                }
            }.attach()
        }
    }

    inner class MyViewPagerAdapter : FragmentStateAdapter(supportFragmentManager, lifecycle) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            val isfollower = position == 0
            return FollowFragment.onSaveInstance(isfollower)
        }
    }

    private fun getData() {
        username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        if (username.isNotEmpty()) {
            viewModel.getDetailUser(username)
        }
        initToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        val menuFav = menu?.findItem(R.id.home_menu_fav)
        menuFav?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home_menu_setting) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}