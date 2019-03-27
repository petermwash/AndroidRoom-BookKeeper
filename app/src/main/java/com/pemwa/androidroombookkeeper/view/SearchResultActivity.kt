package com.pemwa.androidroombookkeeper.view

import android.app.Activity
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.pemwa.androidroombookkeeper.R
import com.pemwa.androidroombookkeeper.adapter.BookListAdapter
import com.pemwa.androidroombookkeeper.data.Book
import com.pemwa.androidroombookkeeper.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class SearchResultActivity: AppCompatActivity(), BookListAdapter.OnDeleteClickListener
    {

        private lateinit var searchViewModel: SearchViewModel
        private var bookListAdapter: BookListAdapter? = null
        private val TAG = this.javaClass.simpleName

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)

            fab.visibility = View.INVISIBLE

            searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

            bookListAdapter = BookListAdapter(this, this)
            recyclerview.adapter = bookListAdapter
            recyclerview.layoutManager = LinearLayoutManager(this)

            handleSearIntent(intent)

        }

        private fun handleSearIntent(intent: Intent) {
            if (Intent.ACTION_SEARCH == intent.action) {
                val searchQuery = intent.getStringExtra(SearchManager.QUERY)

                Log.i(TAG, "Search Query is $searchQuery")

                searchViewModel.getBooksByTitleOrAuthor("%$searchQuery%")?.observe(this, Observer { books ->
                    books?.let { bookListAdapter!!.setBooks(books) }
                })
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                // Code to edit book
                val bookId = data!!.getStringExtra(ID)
                val authorName = data.getStringExtra(UPDATED_AUTHOR)
                val bookName = data.getStringExtra(UPDATED_BOOK)
                val description = data.getStringExtra(UPDATED_DESCRIPTION)

                val book = Book(bookId, authorName, bookName, description)
                searchViewModel.update(book)

                Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
            }
        }

        override fun onDeleteClickListener(myBook: Book) {
            searchViewModel.delete(myBook)
            Toast.makeText(applicationContext, R.string.deleted, Toast.LENGTH_LONG).show()
        }

    }