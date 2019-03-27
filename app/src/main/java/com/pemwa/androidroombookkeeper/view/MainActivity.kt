package com.pemwa.androidroombookkeeper.view

import android.app.Activity
import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.arch.lifecycle.Observer
import android.content.ComponentName
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.pemwa.androidroombookkeeper.R
import com.pemwa.androidroombookkeeper.adapter.BookListAdapter
import com.pemwa.androidroombookkeeper.data.Book
import com.pemwa.androidroombookkeeper.util.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BookListAdapter.OnDeleteClickListener {

    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val bookListAdapter = BookListAdapter(this, this)
        recyclerview.adapter = bookListAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
        }

        viewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
        viewModel.allBooks.observe(this, Observer { books ->
            books?.let {
                bookListAdapter.setBooks(books)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = UUID.randomUUID().toString()
            val authorName = data!!.getStringExtra(NEW_AUTHOR)
            val bookTitle = data.getStringExtra(NEW_BOOK)
            val description = data.getStringExtra(NEW_DESCRIPTION)

            val book = Book(id, authorName, bookTitle, description)

            viewModel.insert(book)

            Toast.makeText(
                applicationContext,
                R.string.saved,
                Toast.LENGTH_LONG
            ).show()

        }else if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = data!!.getStringExtra(ID)
            val authorName = data.getStringExtra(UPDATED_AUTHOR)
            val bookTitle = data.getStringExtra(UPDATED_BOOK)
            val description = data.getStringExtra(NEW_DESCRIPTION)

            val book = Book(id, authorName, bookTitle, description)

            viewModel.update(book)

            Toast.makeText(
                applicationContext,
                R.string.updated,
                Toast.LENGTH_LONG
            ).show()

        } else{
            Toast.makeText(
                applicationContext,
                R.string.not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        //Get the SearchView and and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as android.widget.SearchView

        // Setting the SearchResultActivity to show the result
        val componentName = ComponentName(this, SearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDeleteClickListener(mBook: Book) {
        viewModel.delete(mBook)
        Toast.makeText(
            applicationContext,
            R.string.deleted,
            Toast.LENGTH_LONG
        ).show()
    }
}
