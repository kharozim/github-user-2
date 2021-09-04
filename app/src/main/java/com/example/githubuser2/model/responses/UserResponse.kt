package com.example.githubuser2.model.responses

import com.example.githubuser2.repository.local.UserEntity
import com.google.gson.annotations.SerializedName

data class UserResponse(
    val items: List<UserItem>
)

data class UserItem(
    val id: Int? = null,
    val login: String? = null,
    val name: String? = null,
    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,
    val followers: Int? = null,
    val following: Int? = null,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    val company: String? = null,
    val location: String? = null,
    val email: String? = null
)

fun UserItem.toUserEntity(): UserEntity = UserEntity(
    id ?: 0,
    name ?: "",
    login ?: "",
    publicRepos ?: 0,
    followers ?: 0,
    following ?: 0,
    avatarUrl ?: "",
    company ?: "",
    location ?: "",
    email ?: ""
)

