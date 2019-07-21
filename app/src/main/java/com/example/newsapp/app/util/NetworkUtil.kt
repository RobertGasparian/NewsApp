package com.example.newsapp.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


enum class NetworkState {
    NONE,
    MOBILE,
    WIFI
}


fun getConnectionType(context: Context): NetworkState {
    var result = NetworkState.NONE
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = NetworkState.WIFI
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = NetworkState.MOBILE
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = NetworkState.WIFI
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = NetworkState.MOBILE
                }
            }
        }
    }
    return result
}
