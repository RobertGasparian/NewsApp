package com.example.newsapp.viewmodels.delegates

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.ui.fragments.PreviewMode

interface IPreviewVM {
    fun setMode(mode: PreviewMode)
    fun getPreviewXMLString() : LiveData<String>
    fun getPreviewJSONString() : LiveData<String>
    fun save(context: Context) : Boolean
}