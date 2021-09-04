package com.example.githubuser2.ui.views.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ActivityMainBinding
import com.example.githubuser2.model.responses.UserItem
import com.example.githubuser2.repository.contract.UserContractRepoImpl
import com.example.githubuser2.repository.local.AppDatabase
import com.example.githubuser2.repository.remote.RetrofitClient
import com.example.githubuser2.ui.views.detail.DetailActivity
import com.example.githubuser2.ui.views.detail.DetailActivity.Companion.EXTRA_USERNAME
import com.example.githubuser2.ui.views.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val service by lazy { RetrofitClient.userService }
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val userDao by lazy { db.UserDao() }
    private val repository by lazy { UserContractRepoImpl(service, userDao) }
    private val viewModel by lazy {
        ViewModelProvider(this, HomeViewModelFactory(repository)).get(
            HomeVIewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel.searchUser("kharozim")
        setObserver()
        setview()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_menu_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.home_menu_fav -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setview() {
        binding.edtHomeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query ?: "kharozim")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchUser(newText ?: "kharozim")
                return false
            }
        })
    }

    private fun setObserver() {
        viewModel.userList.observe(this, { result ->
            setDataUser(result.items)
        })

        viewModel.isLoading.observe(this, { isLoading ->
            binding.progressLoading.isVisible = isLoading
        })
    }

    private fun setDataUser(items: List<UserItem>) {
        val adapter = UserAdapter(items)
        adapter.onClickItem(object : UserAdapter.MyListener {
            override fun onClicked(position: Int, data: Any?) {
                data as UserItem
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })
        binding.rvListUser.adapter = adapter
        binding.rvListUser.scheduleLayoutAnimation()
    }

}