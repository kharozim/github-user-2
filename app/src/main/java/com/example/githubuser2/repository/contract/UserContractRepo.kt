package com.example.githubuser2.repository.contract

import com.example.githubuser2.model.responses.UserItem
import com.example.githubuser2.model.responses.UserResponse
import com.example.githubuser2.repository.local.UserEntity

interface UserContractRepo {
    suspend fun searchUser(username: String): UserResponse
    suspend fun getDetailUser(username: String): UserItem
    suspend fun getUserFollower(username: String): List<UserItem>
    suspend fun getUserFollowing(username: String): List<UserItem>

    suspend fun insertUserFav(user: UserEntity)
    suspend fun getAllUserFav(): List<UserEntity>
    suspend fun deleteUserFav(userId: Int)
    suspend fun findUserFav(userId: Int): UserEntity?
}