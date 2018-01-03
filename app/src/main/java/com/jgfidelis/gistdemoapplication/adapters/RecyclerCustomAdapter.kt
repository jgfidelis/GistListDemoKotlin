package com.jgfidelis.gistdemoapplication.adapters

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jgfidelis.gistdemoapplication.BuildConfig
import com.jgfidelis.gistdemoapplication.MainActivity
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.fragments.gistDetails.GistDetailFragment
import com.jgfidelis.gistdemoapplication.inflate
import com.jgfidelis.gistdemoapplication.models.GistModelObject
import kotlinx.android.synthetic.main.cell_layout.view.*

/**
 * Created by jgfidelis on 20/12/17.
 */
class RecyclerCustomAdapter(private var gistsList: ArrayList<GistModelObject>, fragAdapterPosition: Int, hasPagination:Boolean) : Adapter<RecyclerCustomAdapter.ViewHolder>() {
    private val fragAdaperPosition:Int = fragAdapterPosition
    private val hasPagination = hasPagination

    private var isUpdatingList:Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflatedView = parent.inflate(R.layout.cell_layout, false)
        return ViewHolder(inflatedView, fragAdaperPosition)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == gistsList.size && hasPagination) {
            holder.showProgress()
        } else {
            val gist = gistsList[position]
            holder.bindGist(gist)
        }
    }

    override fun getItemCount():Int {
        if (hasPagination) {
            return gistsList.size+1
        } else {
            return gistsList.size
        }
    }

    fun prepareListForUpdate() {
        isUpdatingList = true
    }

    fun updateFailed() {
        isUpdatingList = false
    }

    fun isUpdatingList(): Boolean {
        return isUpdatingList
    }

    fun updateList(gistsList: ArrayList<GistModelObject>) {
        this.gistsList = gistsList
        isUpdatingList = false
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View, page:Int) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private val page:Int = page

        private var gist: GistModelObject? = null

        init {
            itemView.mybtn.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val mainActivity = itemView.context as MainActivity
            mainActivity.switchContent(page, GistDetailFragment.newInstance(this.gist!!, page))
        }

        fun showProgress() {
            view.nameText.visibility = View.GONE
            view.authorText.visibility = View.GONE
            view.mybtn.visibility = View.GONE
            view.imageView.visibility = View.GONE
            view.gistDesc.visibility = View.GONE
            view.progressBar.visibility = View.VISIBLE
        }

        fun bindGist(gist: GistModelObject) {
            this.gist = gist

            view.nameText.visibility = View.VISIBLE
            view.authorText.visibility = View.VISIBLE
            view.mybtn.visibility = View.VISIBLE
            view.imageView.visibility = View.VISIBLE
            view.gistDesc.visibility = View.VISIBLE
            view.progressBar.visibility = View.GONE

            view.nameText.text = gist.title
            view.authorText.text = String.format(itemView.context.resources.getString(R.string.by), gist.authorName)
            view.gistDesc.text = gist.description ?: ""
            if (gist.authorUrl != null) {
                Glide.with(view.context).load(gist.authorUrl).into(view.imageView)
            } else {
                val drawableId = if (BuildConfig.FLAVOR == "red") R.drawable.unknown_red else R.drawable.unkown_blue
                val bm = BitmapFactory.decodeResource(itemView.context.resources, drawableId)
                view.imageView.setImageBitmap(bm)
            }
        }
    }
}