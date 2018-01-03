package com.jgfidelis.gistdemoapplication.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jgfidelis.gistdemoapplication.MainActivity
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.fragments.fileDetails.FileDetailFragment
import com.jgfidelis.gistdemoapplication.inflate
import com.jgfidelis.gistdemoapplication.models.FileModelObject
import kotlinx.android.synthetic.main.file_cell_layout.view.*

/**
 * Created by jgfidelis on 21/12/17.
 */

class FileDetailRecyclerAdapter(private val filesList: ArrayList<FileModelObject>, fragAdapterPosition: Int) : RecyclerView.Adapter<FileDetailRecyclerAdapter.ViewHolder>() {
    private val fragAdaperPosition:Int = fragAdapterPosition
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.file_cell_layout, false)
        return ViewHolder(inflatedView, fragAdaperPosition)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = filesList[position]
        holder.bindFile(file)
    }

    override fun getItemCount() = filesList.size


    class ViewHolder(itemView: View, page:Int) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private val page:Int = page

        private var file: FileModelObject? = null

        init {
            itemView.mybtn.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val mainActivity = itemView.context as MainActivity
            mainActivity.switchContent(page, FileDetailFragment.newInstance(this.file!!))
        }

        fun bindFile(file: FileModelObject) {
            this.file = file
            view.nameText.text = file.fileName
            view.languageText.text = file.language
        }
    }
}