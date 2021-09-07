package com.kharozim.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: Int,
    val name: String,
    val login: String,
    val avatarUrl: String,
    val email: String
) : Parcelable
