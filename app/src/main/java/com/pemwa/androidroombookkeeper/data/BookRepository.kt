package com.pemwa.androidroombookkeeper.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class BookRepository(application: Application) {

    val allBooks: LiveData<List<Book>>
    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.allBooks
    }

    fun insert(book: Book) {
        InsertAsyncTask(bookDao).execute(book)
    }

    fun update(book: Book) {
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book) {
        DeleteAsyncTask(bookDao).execute(book)
    }

    fun getBooksByTitleOrAuthor(searchQuery: String): LiveData<List<Book>>? {
        return bookDao.getBooksByTitleOrAuthor(searchQuery)
    }

    companion object {
        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {

            override fun doInBackground(vararg books: Book): Void? {
                bookDao.insert(books[0])
                return null
            }
        }

        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {

            override fun doInBackground(vararg books: Book): Void? {
                bookDao.update(books[0])
                return null
            }
        }

        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.delete(books[0])
                return null
            }
        }

    }
}