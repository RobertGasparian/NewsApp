package com.example.newsapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsapp.data.models.INews
import com.example.newsapp.viewmodels.DetailedViewModel
import com.example.newsapp.viewmodels.delegates.IDetailedVM
import kotlinx.android.synthetic.main.fragmnet_detailed.*

class DetailedFragment : BaseFragment() {

    companion object {
        val TAG = this::class.java.simpleName
        private const val KEY_NEWS_KEY = "news_key"

        fun newInstance(mergedNews: INews): DetailedFragment {
            val args = Bundle()
            args.putSerializable(KEY_NEWS_KEY, mergedNews)
            val fragment = DetailedFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var model: IDetailedVM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.newsapp.R.layout.fragmnet_detailed, container, false)
    }

    override fun setupViews() {
        webView.webViewClient = (object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoadingDialog()
                urlAddressTV.text = url
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dismissLoadingDialog()
                backBtn.isEnabled = webView.canGoBack()
                forwardBtn.isEnabled = webView.canGoForward()
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                Toast.makeText(context, "Got Error! $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun bindViewModel() {
        model = ViewModelProviders.of(this).get(DetailedViewModel::class.java)
        model?.setupInitialNewsValue(arguments?.getSerializable(KEY_NEWS_KEY) as? INews)
        model?.getMergedNews()?.observe(this, Observer<INews> {
            webView.loadUrl(it.getILink())
        })
    }

    override fun setupListeners() {
        backBtn.setOnClickListener {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
            }
        }
        forwardBtn.setOnClickListener {
            if (webView.canGoForward()) {
                webView.goForward()
            } else {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}