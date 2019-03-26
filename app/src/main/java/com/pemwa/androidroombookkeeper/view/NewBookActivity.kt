package com.pemwa.androidroombookkeeper.view

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.pemwa.androidroombookkeeper.R
import com.pemwa.androidroombookkeeper.util.NEW_AUTHOR
import com.pemwa.androidroombookkeeper.util.NEW_BOOK
import kotlinx.android.synthetic.main.activity_new_book.*

class NewBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        bSave.setOnClickListener {
            val resultIntent = Intent()

            if (TextUtils.isEmpty(etAuthorName.text) ||
                    TextUtils.isEmpty(etBookName.text)) {
                setResult(Activity.RESULT_CANCELED, resultIntent)
            }else{
                val author = etAuthorName.text.toString()
                val book = etBookName.text.toString()

                resultIntent.putExtra(NEW_AUTHOR, author)
                resultIntent.putExtra(NEW_BOOK, book)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }

        bCancel.setOnClickListener {
            finish()
        }
    }

}
