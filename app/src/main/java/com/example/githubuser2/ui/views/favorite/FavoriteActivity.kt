package com.example.githubuser2.ui.views.favorite

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ActivityFavoriteBinding
import com.example.githubuser2.repository.contract.UserContractRepoImpl
import com.example.githubuser2.repository.local.AppDatabase
import com.example.githubuser2.repository.local.UserEntity
import com.example.githubuser2.repository.remote.RetrofitClient
import com.example.githubuser2.ui.views.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val service by lazy { RetrofitClient.userService }
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val userDao by lazy { db.UserDao() }
    private val repository by lazy { UserContractRepoImpl(service, userDao) }
    private val viewModel by lazy {
        ViewModelProvider(this, FavoriteViewModelFactory(repository)).get(
            FavoriteViewModel::class.java
        )
    }
    private val adapter by lazy { FavoriteAdapter() }
    private val listUser = ArrayList<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initToolbar()
        setObserver()
        setView()

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.run {
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }


    private fun getData() {
        listUser.clear()
        viewModel.getAllUserFav()
    }

    private fun setObserver() {
        allUserFavObserver()
    }

    private fun allUserFavObserver() {
        viewModel.userFav.observe(this, { result ->
            setAllUser(result ?: emptyList())
        })
    }

    private fun setAllUser(list: List<UserEntity>) {
        listUser.addAll(list)
        doFilter("")
    }

    private fun setView() {
        binding.run {
            adapter.clickItem(object : FavoriteAdapter.ItemClick {
                override fun onItemClick(position: Int, data: Any?) {
                    data as UserEntity
                    val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(intent)
                }
            })
            rvListFav.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    doFilter(text ?: "")
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun doFilter(text: String) {
        val filtered = listUser.asSequence().filter { it.login.contains(text, true) }.toList()
        adapter.items = filtered
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}