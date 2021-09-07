package com.example.githubuser2.ui.utils.constants

import android.database.Cursor
import com.example.githubuser2.repository.local.UserEntity

object MapperUtil {
    fun cursorToArraylist(cursor: Cursor?): List<UserEntity> {
        val list = ArrayList<UserEntity>()

        if (cursor?.moveToNext() == true) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.NAME))
            val login =
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.LOGIN))
            val avatar =
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.AVATAR_URL))
            val email =
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.EMAIL))

            list.add(
                UserEntity(
                    id = id,
                    name = name,
                    login = login,
                    avatarUrl = avatar,
                    email = email
                )
            )
        }
        return list
    }
}