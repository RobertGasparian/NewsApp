package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.models.INews
import com.example.newsapp.ui.viewholders.NewsHolder

class NewsAdapter(var newsList: List<INews>) : RecyclerView.Adapter<NewsHolder>() {

    var clickListener: NewsClickListener? = null

    fun setItemClickListener(listener: NewsClickListener) {
        clickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
        holder.v.setOnClickListener {
            clickListener?.onItemClick(news, position)
        }
    }

}

interface NewsClickListener {
    fun onItemClick(news: INews, position: Int)
}