package com.jgfidelis.gistdemoapplication.fragments.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.adapters.RecyclerCustomAdapter
import com.jgfidelis.gistdemoapplication.models.GistModelObject
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private val presenter = FavoritesFragmentPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            presenter.fragmentWillShow()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewFavs.layoutManager = LinearLayoutManager(context)
        val dividerDrawable = ContextCompat.getDrawable(activity, R.drawable.divider)
        val divider = DividerItemDecoration(activity, LinearLayout.VERTICAL)
        divider.setDrawable(dividerDrawable)
        recyclerViewFavs.addItemDecoration(divider)
        val adapter = RecyclerCustomAdapter(ArrayList<GistModelObject>(), 1, false)
        recyclerViewFavs.adapter = adapter
        presenter.fragmentWillShow()
    }

    fun updateList(array:ArrayList<GistModelObject>) {
        val adapter = recyclerViewFavs.adapter as RecyclerCustomAdapter
        adapter.updateList(array)
    }

    fun showEmptyMessage() {
        recyclerViewFavs.visibility = View.GONE
        message.visibility = View.VISIBLE
    }

    fun hideEmptyMessage() {
        recyclerViewFavs.visibility = View.VISIBLE
        message.visibility = View.GONE
    }

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

}// Required empty public constructor
