package com.zoey.firebaselogintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    private var database: FirebaseDatabase? = null
    private var myRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        database = FirebaseDatabase.getInstance()
        myRef = database?.getReference("message")
        var user = FirebaseAuth.getInstance().currentUser
        article_write_button.setOnClickListener {

            var title = article_title_inputtext.text.toString()
            var content = article_content_inputtext.text.toString()
            var article = Article(title, content)
            myRef?.child(user?.uid!!)?.child("article")?.push()?.setValue(article)
            myRef
        }
    }
}