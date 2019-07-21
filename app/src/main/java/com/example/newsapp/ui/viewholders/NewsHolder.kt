package com.example.newsapp.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.managers.InMemoryCache
import com.example.newsapp.data.models.INews

class NewsHolder(val v: View) : RecyclerView.ViewHolder(v) {

    private var titleTV: TextView = v.findViewById(R.id.titleTv)
    private var descriptionTV: TextView = v.findViewById(R.id.descriptionTV)
    private var thumbnailIV: ImageView = v.findViewById(R.id.thumbnailIV)

    fun bind (news: INews) {
        titleTV.text = news.getITitle()
        descriptionTV.text = news.getIDescription()
        news.getIThumbNail()?.let {
            Glide.with(v.context)
                .load(it)
                .into(thumbnailIV)
        } ?: run {
            InMemoryCache.BBC_THUMBNAIL_URL?.let {
                Glide.with(v.context)
                    .load(it)
                    .into(thumbnailIV)
            } ?: run {
                thumbnailIV.setImageResource(R.drawable.placeholder_img)
            }
        }
    }
}