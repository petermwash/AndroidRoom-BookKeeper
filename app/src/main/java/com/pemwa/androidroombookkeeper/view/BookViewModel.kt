package com.pemwa.androidroombookkeeper.view

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.pemwa.androidroombookkeeper.data.Book
import com.pemwa.androidroombookkeeper.data.BookRepository

class BookViewModel(application: Application): AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>
    private val bookRepository = BookRepository(application)

    init {
        allBooks = bookRepository.allBooks
    }

    fun insert(book: Book) {
        bookRepository.insert(book)
    }

    fun update(book: Book) {
        bookRepository.update(book)
    }

    fun delete(book: Book) {
        bookRepository.delete(book)
    }
}
