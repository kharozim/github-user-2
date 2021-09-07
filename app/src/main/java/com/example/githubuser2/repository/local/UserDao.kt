package com.example.githubuser2.repository.local

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user_entity")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM user_entity WHERE login LIKE :name")
    suspend fun findByName(name: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserFav(vararg user: UserEntity)

    @Query("DELETE FROM user_entity WHERE id = :userId")
    suspend fun removeFav(userId: Int)

    @Query("SELECT * FROM user_entity WHERE id = :userId")
    suspend fun findUserFav(userId: Int): UserEntity?

    @Query("SELECT * FROM user_entity")
    fun getCursor(): Cursor

}