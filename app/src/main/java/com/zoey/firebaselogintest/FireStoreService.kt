package com.zoey.firebaselogintest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FireStoreService {

    private lateinit var fireStoreInstance :FirebaseFirestore
    private lateinit var fireBaseUser: FirebaseUser
    private lateinit var firestoreSetting: FirebaseFirestoreSettings

    init {
        firestoreSetting = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        fireStoreInstance = FirebaseFirestore.getInstance()
        fireBaseUser = FirebaseAuth.getInstance().currentUser!!
        fireStoreInstance.firestoreSettings = firestoreSetting
    }

    fun saveArticleQuery(article: Article) {
        fireStoreInstance.collection("users")
            .document(fireBaseUser!!.uid)
            .collection("article")
            .add(article)
            .addOnSuccessListener {
                Log.d(Constants.TAG, "SaveArticle Success")
            }
            .addOnFailureListener {
                Log.d(Constants.TAG, "SaveArticle Failure")
            }
    }

    fun loadAllArticleQuery(): ArrayList<Article> {

        var articles: ArrayList<Article> = arrayListOf()

        fireStoreInstance.collection("users")
            .document(fireBaseUser.uid)
            .collection("article")
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.forEach {

                    val title = it.get("title").toString()
                    val content = it.get("content").toString()
                    articles.add(Article(title = title, content = content))
                }
            }
            .addOnFailureListener{
                Log.d(Constants.TAG, "Exception : ${it.toString()}")
            }

        return articles
    }

}