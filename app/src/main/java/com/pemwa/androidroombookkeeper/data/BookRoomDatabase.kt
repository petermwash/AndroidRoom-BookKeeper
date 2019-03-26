package com.pemwa.androidroombookkeeper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Book::class], version = 1)
abstract class BookRoomDatabase : RoomDatabase(){

    abstract fun bookDao(): BookDao

    companion object {

        private var bookRoomDatabaseInstance : BookRoomDatabase? = null

        fun getDatabase(context: Context) : BookRoomDatabase? {
            if (bookRoomDatabaseInstance == null) {
                synchronized(BookRoomDatabase::class.java) {
                    if (bookRoomDatabaseInstance == null) {
                        bookRoomDatabaseInstance = Room.databaseBuilder<BookRoomDatabase>(context.applicationContext,
                            BookRoomDatabase::class.java, "book_database")
                            .build()
                    }
                }
            }
            return bookRoomDatabaseInstance
        }
    }
}