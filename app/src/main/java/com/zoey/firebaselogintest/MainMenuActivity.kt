package com.zoey.firebaselogintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: MainRecyclerViewAdapter
    private lateinit var articles: ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        articles = FireStoreService.loadAllArticleQuery()


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewAdapter = MainRecyclerViewAdapter()

        article_recyclerview.layoutManager = layoutManager
        article_recyclerview.adapter = recyclerViewAdapter
        recyclerViewAdapter.submitList(articles)

        article_write_button.setOnClickListener {
            val article = Article(
                title = article_title_inputtext.text.toString(),
                content = article_content_inputtext.text.toString()
            )

            FireStoreService.saveArticleQuery(article)
            articles = FireStoreService.loadAllArticleQuery()
            Log.d(Constants.TAG, articles.toString())
        }
    }

}