package com.example.newsapp.ui.fragments.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.newsapp.R

class LoadingDialog : DialogFragment() {
    companion object {
        val TAG = this::class.java.simpleName

        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_loading, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        return view
    }

    override fun onStart() {
        super.onStart()
        //Changing dark background and removing dialog borders

        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val windowParams = window.attributes
            windowParams.dimAmount = 0.5f
            windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            window.attributes = windowParams
        }
    }
}