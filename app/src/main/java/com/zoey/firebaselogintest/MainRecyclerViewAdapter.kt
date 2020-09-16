package com.zoey.firebaselogintest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewHolder>() {

    private var articles: List<Article> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)

        return MainRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        holder.bindView(articles[position])
    }

    fun getArticle(articles: List<Article>) {
        this.articles = articles
    }
}