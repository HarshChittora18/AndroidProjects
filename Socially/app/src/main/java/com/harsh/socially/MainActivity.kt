package com.harsh.socially

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.harsh.socially.daos.PostDao
import com.harsh.socially.models.IPostAdapter
import com.harsh.socially.models.Post
import com.harsh.socially.models.PostAdapter

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var adapter: PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabf = findViewById<ImageView>(R.id.fab)
        fabf.setOnClickListener{
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
    }

     private fun setUpRecyclerView() {
         postDao = PostDao()
         val postsCollection = postDao.postCollection
         val query = postsCollection.orderBy("createdAt", Query.Direction.DESCENDING)
         val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

         val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
         recyclerview.adapter = adapter
         recyclerview.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}