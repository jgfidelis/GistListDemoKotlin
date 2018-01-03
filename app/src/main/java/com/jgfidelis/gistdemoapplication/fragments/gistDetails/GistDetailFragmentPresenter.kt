package com.jgfidelis.gistdemoapplication.fragments.gistDetails

import com.jgfidelis.gistdemoapplication.models.GistModelObject
import com.jgfidelis.gistdemoapplication.utils.FavoritesManager

/**
 * Created by jgfidelis on 23/12/17.
 */
class GistDetailFragmentPresenter(frag: GistDetailFragment) {
    private val frag: GistDetailFragment = frag

    private var gist:GistModelObject? = null

    fun onActivityCreated() {
        val json = frag.arguments.getString("gist")
        if (gist == null) gist = GistModelObject.convertJsonToGist(json)
        frag.bindView(gist!!)
        val page:Int = frag.arguments.getInt("page")
        frag.setAdapter(page, gist!!.files)
    }

    fun fragmentWillShow() {
        if (gist != null) frag.setFavoriteButtonState(gist!!)
    }



    fun clickedFavoriteButton() {
        val isGistFavorite = FavoritesManager.getInstance(frag.activity).isGistFavorite(gist!!.gid)
        if (isGistFavorite) {
            FavoritesManager.getInstance(frag.activity).removeFavorite(gist!!.gid)
        } else {
            FavoritesManager.getInstance(frag.activity).saveFavorite(gist!!)
        }
        frag.startStarFadeAnimation(!isGistFavorite)
    }
}