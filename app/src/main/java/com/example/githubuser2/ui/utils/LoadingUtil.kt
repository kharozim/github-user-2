package com.example.githubuser2.ui.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import com.example.githubuser2.R
import com.example.githubuser2.databinding.LayoutLoadingUtilBinding

class LoadingUtil(private val context : Context) {
    private val dialog = Dialog(context, R.style.Theme_AppCompat_Dialog_MinWidth)
    private val binding = LayoutLoadingUtilBinding.inflate(LayoutInflater.from(context))

    init {
        dialog.window?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                android.R.color.transparent,
                null
            )
        )
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)
    }

    fun showLoading(message: String = "") {
        if (!(context as Activity).isDestroyed) {
            binding.textLoading.text = message
        }
        dialog.show()
    }

    fun hideLoading() {
        dialog.dismiss()
    }

}