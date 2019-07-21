package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.app.util.NetworkState
import com.example.newsapp.app.util.getConnectionType
import com.example.newsapp.ui.activities.tools.FragmentChangeListener
import com.example.newsapp.ui.fragments.dialogs.LoadingDialog

abstract class BaseFragment : Fragment() {

    private var loadingFragment: LoadingDialog? = null
    protected var changer: FragmentChangeListener? = null
    private var isShownErrorDialog: Boolean = false
    private var numberOfRequestedLoadingDialog: Int = 0

    fun setChangeListener(listener: FragmentChangeListener) {
        changer = listener
    }

    private fun setupLoadingDialog() {
        loadingFragment = LoadingDialog.newInstance()
        loadingFragment!!.isCancelable = false
    }

    fun showLoadingDialog() {
        numberOfRequestedLoadingDialog++
        if (numberOfRequestedLoadingDialog == 1) {
            setupLoadingDialog()
            loadingFragment?.show(childFragmentManager, null)
        }
    }

    fun dismissLoadingDialog() {
        if (numberOfRequestedLoadingDialog == 1) {
            loadingFragment?.dismiss()
            numberOfRequestedLoadingDialog--
        } else if (numberOfRequestedLoadingDialog > 1) {
            numberOfRequestedLoadingDialog--
        }
    }

    fun showErrorDialog(errorMessage: String? = null) {
        var message = errorMessage ?: "Unknown error"
        if (getConnectionType(context!!) == NetworkState.NONE) {
            message = getString(R.string.no_inet_message)
        }

        if (!isShownErrorDialog) {
            isShownErrorDialog = true
            if (numberOfRequestedLoadingDialog >= 1) {
                loadingFragment?.dismiss()
            }
            AlertDialog.Builder(context!!)
                .setTitle("Error")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    dialog.dismiss()
                    if (numberOfRequestedLoadingDialog > 0) {
                        loadingFragment?.show(childFragmentManager, null)
                    }
                    isShownErrorDialog = false
                }.create().show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindViewModel()
        setupListeners()
    }

    abstract fun setupViews()
    abstract fun bindViewModel()
    abstract fun setupListeners()
}