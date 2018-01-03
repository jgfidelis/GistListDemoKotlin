package com.jgfidelis.gistdemoapplication.fragments.gistDetails


import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.jgfidelis.gistdemoapplication.BuildConfig
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.adapters.FileDetailRecyclerAdapter
import com.jgfidelis.gistdemoapplication.convertToJson
import com.jgfidelis.gistdemoapplication.models.FileModelObject
import com.jgfidelis.gistdemoapplication.models.GistModelObject
import com.jgfidelis.gistdemoapplication.utils.FavoritesManager
import kotlinx.android.synthetic.main.fragment_gist_detail.*

class GistDetailFragment : Fragment() {

    private val presenter: GistDetailFragmentPresenter = GistDetailFragmentPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gist_detail, container, false)
    }

    fun setFavoriteButtonState(gist: GistModelObject) {
        button.setState(FavoritesManager.getInstance(activity).isGistFavorite(gist.gid))
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            presenter.fragmentWillShow()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onActivityCreated()
        var callback = object: View.OnClickListener {
            override fun onClick(p0: View?) {
                presenter.clickedFavoriteButton()
            }
        }
        button.setButtonListener(callback)
        recyclerViewGist.layoutManager = LinearLayoutManager(context)
        val dividerDrawable = ContextCompat.getDrawable(activity, R.drawable.divider)
        val divider = DividerItemDecoration(activity, LinearLayout.VERTICAL)
        divider.setDrawable(dividerDrawable)
        recyclerViewGist.addItemDecoration(divider)
    }

    fun startStarFadeAnimation(isFavorite:Boolean) {
        button.fadeTransition(isFavorite)
    }

    fun bindView(gist:GistModelObject) {
        authorText.text = String.format(activity.resources.getString(R.string.by), gist.authorName)
        nameTextGist.text = gist.title
        description.text = gist.description
        if (gist.files.size == 1) {
            fileCount.text = resources.getString(R.string.file)
        } else {
            fileCount.text = String.format(resources.getString(R.string.file_pl), gist.files.size)
        }

        if (gist.description == "" || gist.description == null) {
            nameTextGist.visibility = View.GONE
            description.visibility = View.GONE
            nameTextMult.visibility = View.VISIBLE
            nameTextMult.text = gist.title
        } else {
            nameTextMult.visibility = View.GONE
        }

        if (gist.authorUrl != null) {
            Glide.with(activity).load(gist.authorUrl).into(imageView)
        } else {
            val drawableId = if (BuildConfig.FLAVOR == "red") R.drawable.unknown_red else R.drawable.unkown_blue
            val bitmap = BitmapFactory.decodeResource(activity.resources, drawableId)
            imageView.setImageBitmap(bitmap)
        }

        setFavoriteButtonState(gist)
    }

    fun setAdapter(page:Int, files:ArrayList<FileModelObject>) {
        val adapter = FileDetailRecyclerAdapter(files, page)
        recyclerViewGist.adapter = adapter
    }

    companion object {
        fun newInstance(gist: GistModelObject, page:Int): GistDetailFragment {
            val fragment = GistDetailFragment()
            val args = Bundle()
            val json = convertToJson(gist)
            args.putString("gist", json)
            args.putInt("page", page)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
