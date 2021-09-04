package com.example.githubuser2.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubuser2.model.responses.UserItem

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "public_repos") val publicRepos: Int,
    @ColumnInfo(name = "followers") val followers: Int,
    @ColumnInfo(name = "following") val following: Int,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "email") val email: String
)

fun UserEntity.toUserItem(): UserItem = UserItem(
    id,
    name,
    login,
    publicRepos,
    followers,
    following,
    avatarUrl,
    company,
    location,
    email
)