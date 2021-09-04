package com.example.githubuser2.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao

    companion object {
        private val DB_NAME = "github_user.db"

        @Volatile
        private var DB: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val temp = DB
            if (temp != null) {
                return temp
            } else {
                synchronized(this) {
                    val db = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    DB = db
                    return db
                }
            }
        }
    }
}