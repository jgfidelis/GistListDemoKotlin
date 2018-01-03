package com.jgfidelis.gistdemoapplication.fragments.favorite

import com.jgfidelis.gistdemoapplication.utils.FavoritesManager

/**
 * Created by jgfidelis on 23/12/17.
 */
class FavoritesFragmentPresenter(frag: FavoritesFragment) {
    private val frag = frag

    fun fragmentWillShow() {
        val gists = FavoritesManager.getInstance(frag.activity).getFavoritesList()
        if (gists.size > 0) {
            frag.hideEmptyMessage()
            frag.updateList(gists)
        } else {
            frag.showEmptyMessage()
        }
    }
}