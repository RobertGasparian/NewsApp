package com.example.newsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.ui.activities.tools.FragmentChangeListener
import com.example.newsapp.ui.fragments.BaseFragment
import com.example.newsapp.ui.fragments.MasterFragment

class MainActivity : AppCompatActivity(), FragmentChangeListener {

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setInitialFragment()
    }

    private fun setInitialFragment() {
        val fragment = MasterFragment.newInstance()
        fragment.setChangeListener(this@MainActivity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_layout, fragment)
            .commit()
    }

    override fun onChange(fragment: BaseFragment) {
        fragment.setChangeListener(this@MainActivity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_layout, fragment)
            .addToBackStack(null)
            .commit()
    }
}
