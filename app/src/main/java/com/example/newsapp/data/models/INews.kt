package com.example.newsapp.data.models

import java.io.Serializable

interface INews : Serializable {

    fun getITitle() : String
    fun getIDescription() : String
    fun getILink() : String
    fun getIDateTS() : Long
    fun getIThumbNail() : String?
}