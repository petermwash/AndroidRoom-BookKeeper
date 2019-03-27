package com.pemwa.androidroombookkeeper.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pemwa.androidroombookkeeper.R
import com.pemwa.androidroombookkeeper.util.ID
import com.pemwa.androidroombookkeeper.util.UPDATED_AUTHOR
import com.pemwa.androidroombookkeeper.util.UPDATED_BOOK
import com.pemwa.androidroombookkeeper.util.UPDATED_DESCRIPTION
import kotlinx.android.synthetic.main.activity_new_book.*

class EditBookActivity : AppCompatActivity() {

    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        val bundle: Bundle? = intent.extras

        bundle?.let {
            id = bundle.getString("id")
            val book: String? = bundle.getString("book")
            val author: String? = bundle.getString("author")
            val description: String? = bundle.getString("description")

            etAuthorName.setText(author)
            etBookName.setText(book)
            etDescription.setText(description)
        }

        bSave.setOnClickListener {
            val updatedAuthor: String = etAuthorName.text.toString()
            val updatedBook: String = etBookName.text.toString()
            val updatedDescription: String = etDescription.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra(ID, id)
            resultIntent.putExtra(UPDATED_AUTHOR, updatedAuthor)
            resultIntent.putExtra(UPDATED_BOOK, updatedBook)
            resultIntent.putExtra(UPDATED_DESCRIPTION, updatedDescription)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }

        bCancel.setOnClickListener {
            finish()
        }
    }
}