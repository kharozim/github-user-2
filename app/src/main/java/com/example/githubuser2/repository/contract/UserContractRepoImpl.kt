package com.example.githubuser2.repository.contract

import com.example.githubuser2.model.responses.UserItem
import com.example.githubuser2.model.responses.UserResponse
import com.example.githubuser2.repository.local.UserDao
import com.example.githubuser2.repository.local.UserEntity
import com.example.githubuser2.repository.remote.services.UserService

class UserContractRepoImpl(
    private val service: UserService,
    private val userDao: UserDao
) : UserContractRepo {
    override suspend fun searchUser(username: String): UserResponse {
        return service.searchUser(username)
    }

    override suspend fun getDetailUser(username: String): UserItem {
        return service.getDetailUser(username)
    }

    override suspend fun getUserFollower(username: String): List<UserItem> {
        return service.getUserFollowers(username)
    }

    override suspend fun getUserFollowing(username: String): List<UserItem> {
        return service.getUserFollowing(username)
    }

    override suspend fun insertUserFav(user: UserEntity) {
        return userDao.addUserFav(user)
    }

    override suspend fun getAllUserFav(): List<UserEntity> {
        return userDao.getAll()
    }

    override suspend fun deleteUserFav(userId: Int) {
        return userDao.removeFav(userId)
    }

    override suspend fun findUserFav(userId: Int): UserEntity? {
        return userDao.findUserFav(userId)
    }
}