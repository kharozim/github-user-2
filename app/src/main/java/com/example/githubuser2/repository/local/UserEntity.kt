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
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "email") val email: String
)

fun UserEntity.toUserItem(): UserItem = UserItem(
    id,
    name,
    login,
    avatarUrl = avatarUrl,
    email = email
)