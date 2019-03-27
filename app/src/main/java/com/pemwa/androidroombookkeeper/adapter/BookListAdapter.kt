package com.pemwa.androidroombookkeeper.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pemwa.androidroombookkeeper.view.EditBookActivity
import com.pemwa.androidroombookkeeper.R
import com.pemwa.androidroombookkeeper.data.Book
import com.pemwa.androidroombookkeeper.util.UPDATE_BOOK_ACTIVITY_REQUEST_CODE
import kotlinx.android.synthetic.main.list_item.view.*

class BookListAdapter(private val context: Context,
                      private val onDeleteClickListener : OnDeleteClickListener) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var bookList: List<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.BookViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: BookListAdapter.BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book.author, book.book, position)
        holder.setListeners()
    }

    fun setBooks(books: List<Book>) {
        bookList = books
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var pos = 0

        fun bind(author: String, title: String, position: Int) {
            itemView.tvAuthor.text = author
            itemView.tvBook.text = title
            this.pos = position
        }

        fun setListeners() {
            itemView.setOnClickListener {
                val intent = Intent(context, EditBookActivity::class.java)
                intent.putExtra("id", bookList[pos].id)
                intent.putExtra("author", bookList[pos].author)
                intent.putExtra("book", bookList[pos].book)
                intent.putExtra("description", bookList[pos].description)
                (context as Activity).startActivityForResult(intent, UPDATE_BOOK_ACTIVITY_REQUEST_CODE)
            }
            itemView.ivRowDelete.setOnClickListener {
                onDeleteClickListener.onDeleteClickListener(bookList[pos])
            }
        }

    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(mBook: Book)
    }
}