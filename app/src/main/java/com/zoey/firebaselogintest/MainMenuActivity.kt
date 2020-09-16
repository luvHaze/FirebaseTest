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

    private var database: FirebaseDatabase? = null
    private var firestore: FirebaseFirestore? = null
    private var myRef: DatabaseReference? = null
    private var user: FirebaseUser? = null
    var articles = arrayListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        firestore = FirebaseFirestore.getInstance()
        var firestoreSetting = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        firestore?.firestoreSettings = firestoreSetting
        database = FirebaseDatabase.getInstance()
        user = FirebaseAuth.getInstance().currentUser

        loadArticle()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MainRecyclerViewAdapter()
        adapter.getArticle(articles)
        article_recyclerview.layoutManager = layoutManager
        article_recyclerview.adapter = adapter

        article_write_button.setOnClickListener {
            saveDatawithFireStore()

        }
    }

    fun loadArticle() {

        firestore?.collection("users")?.document(user!!.uid)?.collection("article")?.get()
        ?.addOnSuccessListener { snapshot ->

            snapshot.documents.forEach {
                val title = it.data?.get("title").toString()
                val content = it.data?.get("content").toString()
                articles.add(Article(title,content))
            }

        }
    }

    fun saveDatawithFireStore() {
        var article: HashMap<String, Any> = hashMapOf()
        article.put("title", article_title_inputtext.text.toString())
        article.put("content", article_content_inputtext.text.toString())

        firestore?.collection("users")?.document(user!!.uid)?.set(mapOf("name" to "kim"))
        firestore?.collection("users")
            ?.document(user!!.uid)?.collection("article")?.add(article)
            ?.addOnSuccessListener {
                Log.d(Constants.TAG, "Doc Sanpshot added with ID : $it.id")
            }
            ?.addOnFailureListener{
                Log.d(Constants.TAG, "Error adding doc : ${it.message}")
            }
    }
}