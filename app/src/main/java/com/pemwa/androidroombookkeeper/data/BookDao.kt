package com.pemwa.androidroombookkeeper.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BookDao {

    @Insert
    fun insert(book: Book)

    /**
     * Using a function to query the DB
     */
//    @Query("SELECT * FROM books")
//    fun getAllBooks(): LiveData<List<Book>>

    /**
     * Using a property to query the DB
     */
    @get:Query("SELECT * FROM books")
    val allBooks: LiveData<List<Book>>

    /**
     * Using a property to query the DB
     */
    @Query("SELECT * FROM books WHERE title LIKE :searchString OR author LIKE :searchString")
    fun getBooksByTitleOrAuthor(searchString: String): LiveData<List<Book>>

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}