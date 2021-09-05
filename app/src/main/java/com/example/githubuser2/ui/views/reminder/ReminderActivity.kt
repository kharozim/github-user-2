package com.example.githubuser2.ui.views.reminder

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ActivityReminderBinding
import timber.log.Timber

class ReminderActivity : AppCompatActivity() {

    private val binding by lazy { ActivityReminderBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.parent_layout, ReminderPreference())
                .commit()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}