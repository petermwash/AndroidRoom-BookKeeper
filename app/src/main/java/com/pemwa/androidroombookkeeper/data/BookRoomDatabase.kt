package com.pemwa.androidroombookkeeper.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context

@Database(entities = [Book::class], version = 2)
abstract class BookRoomDatabase : RoomDatabase(){

    abstract fun bookDao(): BookDao

    companion object {

        private var bookRoomDatabaseInstance : BookRoomDatabase? = null

        val MIGRATION_1_2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books "
                        + "ADD COLUMN description TEXT DEFAULT 'Add Description' "
                        + "NOT NULL")
            }

        }

        fun getDatabase(context: Context) : BookRoomDatabase? {
            if (bookRoomDatabaseInstance == null) {
                synchronized(BookRoomDatabase::class.java) {
                    if (bookRoomDatabaseInstance == null) {
                        bookRoomDatabaseInstance = Room.databaseBuilder<BookRoomDatabase>(context.applicationContext,
                            BookRoomDatabase::class.java, "book_database")
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }
            return bookRoomDatabaseInstance
        }
    }
}