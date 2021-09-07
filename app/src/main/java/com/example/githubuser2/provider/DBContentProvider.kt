package com.example.githubuser2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuser2.repository.local.AppDatabase
import com.example.githubuser2.repository.local.UserDao

class DBContentProvider : ContentProvider() {

    private var userDao: UserDao? = null

    init {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAV_USER_DATA)
    }

    override fun onCreate(): Boolean {
        userDao = context?.let { AppDatabase.getDatabase(it).UserDao() }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor: Cursor? = null
        when (uriMatcher.match(uri)) {
            ID_FAV_USER_DATA -> {
//                CoroutineScope(Dispatchers.IO).launch {
                    cursor = userDao?.getCursor()
                    if (context != null) {
                        cursor?.setNotificationUri(context?.contentResolver, uri)
                    }
//                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor

    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
       return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

    companion object {
        const val AUTHORITY = "com.kharozim.githubuser"
        const val TABLE_NAME = "user_entity"
        const val ID_FAV_USER_DATA = 135
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    }


}