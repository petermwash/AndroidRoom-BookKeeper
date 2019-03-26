package com.pemwa.androidroombookkeeper.view

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.pemwa.androidroombookkeeper.data.Book
import com.pemwa.androidroombookkeeper.data.BookRepository

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository = BookRepository(application)

    fun getBooksByTitleOrAuthor(searchQuery: String): LiveData<List<Book>>? {
        return bookRepository.getBooksByTitleOrAuthor(searchQuery)
    }

    fun update(book: Book) {
        bookRepository.update(book)
    }

    fun delete(book: Book) {
        bookRepository.delete(book)
    }
}