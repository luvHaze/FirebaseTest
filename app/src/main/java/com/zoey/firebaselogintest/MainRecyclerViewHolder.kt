package com.zoey.firebaselogintest

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview.view.*

class MainRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val item_contents_textview = itemView.item_contents_textview
    private val item_title_textview = itemView.item_title_textview

    fun bindView(article: Article) {
        item_contents_textview.text = article.content
        item_title_textview.text = article.title
    }
}