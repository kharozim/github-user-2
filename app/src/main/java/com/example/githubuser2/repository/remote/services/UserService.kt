package com.example.githubuser2.repository.remote.services

import com.example.githubuser2.model.responses.UserItem
import com.example.githubuser2.model.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") search: String
    ): UserResponse

    @GET("/users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String
    ) : UserItem

    @GET("/users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username : String
    ) : List<UserItem>

    @GET("/users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username : String
    ) : List<UserItem>


}