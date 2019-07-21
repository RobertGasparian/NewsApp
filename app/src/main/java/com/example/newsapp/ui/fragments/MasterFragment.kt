package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.models.INews
import com.example.newsapp.network.tools.Resource
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.adapters.NewsClickListener
import com.example.newsapp.viewmodels.MasterViewModel
import com.example.newsapp.viewmodels.delegates.IMasterVM
import kotlinx.android.synthetic.main.fragment_master.*

class MasterFragment : BaseFragment(), NewsClickListener {

    companion object {
        val TAG = this::class.java.simpleName

        fun newInstance(): MasterFragment {
            return MasterFragment()
        }
    }

    private var adapter: NewsAdapter? = null
    private var model: IMasterVM? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_master, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.xml_save -> {
                openPreviewFragment(PreviewMode.XML)
            }
            R.id.json_save -> {
                openPreviewFragment(PreviewMode.JSON)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun setupViews() {
        newsRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun bindViewModel() {
        model = ViewModelProviders.of(activity!!).get(MasterViewModel::class.java)
        model?.getMergedNewsList()?.observe(this, Observer<Resource<List<INews>>> {
            when(it) {
                is Resource.Success -> {
                    dismissLoadingDialog()
                    setupRecyclerView(it.data ?: emptyList())
                }
                is Resource.Error -> {
                    dismissLoadingDialog()
                    showErrorDialog(it.message)
                }
                is Resource.Loading -> {
                    showLoadingDialog()
                }
            }
        })
    }

    private fun openPreviewFragment(mode: PreviewMode) {
        changer?.onChange(PreviewFragment.newInstance(mode))
    }

    override fun setupListeners() {

    }

    private fun setupRecyclerView(newsList: List<INews>) {
        adapter = NewsAdapter(newsList)
        adapter!!.setItemClickListener(this)
        newsRV.adapter = adapter
    }

    override fun onItemClick(news: INews, position: Int) {
        changer?.onChange(DetailedFragment.newInstance(news))
    }
}