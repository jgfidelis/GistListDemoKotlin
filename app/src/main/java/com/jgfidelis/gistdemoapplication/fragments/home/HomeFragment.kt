package com.jgfidelis.gistdemoapplication.fragments.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.adapters.RecyclerCustomAdapter
import com.jgfidelis.gistdemoapplication.models.GistModelObject
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    private val presenter = HomeFragmentPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewHome.layoutManager = LinearLayoutManager(context)
        val dividerDrawable = ContextCompat.getDrawable(activity, R.drawable.divider)
        val divider = DividerItemDecoration(activity, LinearLayout.VERTICAL)
        divider.setDrawable(dividerDrawable)
        recyclerViewHome.addItemDecoration(divider)

        presenter.onActivityCreated()
    }

    fun setAdapterWithList(list:ArrayList<GistModelObject>) {
        val adapter = RecyclerCustomAdapter(list, 0, true)
        recyclerViewHome.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun addSrollListener() {
        recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemIndex = layoutManager.findFirstVisibleItemPosition()
                val adapter = recyclerViewHome.adapter as RecyclerCustomAdapter
                if (visibleItemCount + firstVisibleItemIndex >= totalItemCount && !adapter.isUpdatingList()) {
                    presenter.fetchNextPage()
                }
            }
        })
    }

    fun warnAdapterAboutUpdate() {
        val adapter = recyclerViewHome.adapter as RecyclerCustomAdapter
        adapter.prepareListForUpdate()
    }

    fun warnAdapterUpdateFailed() {
        val adapter = recyclerViewHome.adapter as RecyclerCustomAdapter
        adapter.updateFailed()
    }

    fun updateGistList(gistList:ArrayList<GistModelObject>) {
        val adapter = recyclerViewHome.adapter as RecyclerCustomAdapter
        activity.runOnUiThread({
            adapter.updateList(gistList)
        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
