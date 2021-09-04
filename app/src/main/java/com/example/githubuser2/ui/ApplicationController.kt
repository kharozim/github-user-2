package com.example.githubuser2.ui

import android.app.Application
import com.example.githubuser2.BuildConfig
import timber.log.Timber

class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.IS_RELEASE) {
            Timber.plant(Timber.DebugTree())
        }
    }
}