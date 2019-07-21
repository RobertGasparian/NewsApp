package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsapp.R
import com.example.newsapp.viewmodels.MasterViewModel
import com.example.newsapp.viewmodels.delegates.IPreviewVM
import kotlinx.android.synthetic.main.fragment_preview.*
import java.io.Serializable

class PreviewFragment : BaseFragment() {

    companion object {
        val TAG: String = this::class.java.simpleName
        private const val MODE_KEY = "preview_mode"

        fun newInstance(mode: PreviewMode): PreviewFragment {
            val args = Bundle()
            args.putSerializable(MODE_KEY, mode)
            val fragment = PreviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var model: IPreviewVM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun setupViews() {
        when (arguments?.getSerializable(MODE_KEY) as? PreviewMode) {
            PreviewMode.XML -> {
                titleTv.text = getString(R.string.xml_preview_title)
            }
            PreviewMode.JSON -> {
               titleTv.text = getString(R.string.json_preview_title)
            }
            else -> Log.e(TAG, "unknown mode")
        }
    }

    override fun bindViewModel() {
        model = ViewModelProviders.of(activity!!).get(MasterViewModel::class.java)
        val mode = arguments?.getSerializable(MODE_KEY) as? PreviewMode
        mode?.let {
            model?.setMode(it)
        }
        when (mode) {
            PreviewMode.XML -> {
                model?.getPreviewXMLString()?.observe(this, Observer<String> {
                    setPreviewText(it)
                })
            }
            PreviewMode.JSON -> {
                model?.getPreviewJSONString()?.observe(this, Observer<String> {
                    setPreviewText(it)
                })
            }
            else -> Log.e(TAG, "unknown mode")
        }
    }

    private fun setPreviewText(text: String) {
        previewTV.text = text
    }

    override fun setupListeners() {
        saveBtn.setOnClickListener {
            context?.let {
                val saved = model?.save(it)
                if (saved != null && saved) {
                    Toast.makeText(it, getString(R.string.save_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(it, getString(R.string.save_failure), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}

enum class PreviewMode : Serializable {
    XML,
    JSON
}