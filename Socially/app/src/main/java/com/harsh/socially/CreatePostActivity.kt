package com.harsh.socially

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.harsh.socially.daos.PostDao

class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        postDao = PostDao()

        val postbutton = findViewById<Button>(R.id.postButton)
        postbutton.setOnClickListener {
            val postinput = findViewById<EditText>(R.id.postInput)
            val input = postinput.text.toString().trim()
            if (input.isNotEmpty()) {
                postDao.addPost(input)
                finish()
            }
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

    }
}