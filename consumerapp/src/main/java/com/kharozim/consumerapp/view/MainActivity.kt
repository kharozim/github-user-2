package com.kharozim.consumerapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kharozim.consumerapp.databinding.ActivityMainBinding
import com.kharozim.consumerapp.model.UserModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getAllUserFav(this)
        viewModel.listUser.observe(this, { result ->
            setListUser(result ?: emptyList())
        })

    }

    private fun setListUser(data: List<UserModel>) {
        val adapter = HomeAdapter(data)
        adapter.onClickItem(object : HomeAdapter.MyListener {
            override fun onClicked(position: Int, data: Any?) {
                data as UserModel
                Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()
            }
        })

        binding.rvUserFav.adapter = adapter
    }
}