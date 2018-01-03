package com.jgfidelis.gistdemoapplication.utils

import android.content.Context
import com.jgfidelis.gistdemoapplication.convertToJson
import com.jgfidelis.gistdemoapplication.models.GistModelObject


/**
 * Created by jgfidelis on 21/12/17.
 */


class FavoritesManager private constructor(context: Context) {
    private var gistsList:ArrayList<GistModelObject>
    private val context:Context = context
    init {
        gistsList = ArrayList<GistModelObject>()
        val settings = context.getSharedPreferences("MY_LIST", Context.MODE_PRIVATE)
        val listSaved = settings.getString("gist_list", "")
        if (listSaved != "") {
            val mylist: List<GistModelObject> = GistModelObject.convertJsonToGistArray(listSaved)
            gistsList = ArrayList(mylist)
        }
    }

    fun getFavoritesList(): ArrayList<GistModelObject> {
        return gistsList
    }

    fun saveFavorite(gist: GistModelObject) {
        gistsList.add(gist)
        save()
    }

    fun removeFavorite(gid: String) {
        var position = 0
        for (i in 0..(gistsList.size - 1)) {
            val gist = gistsList[i]
            if (gist.gid == gid) {
                position = i
            }
        }
        gistsList.removeAt(position)
        save()
    }

    fun isGistFavorite(gid: String): Boolean {
        for (i in 0..(gistsList.size - 1)) {
            val gist = gistsList[i]
            if (gist.gid == gid) {
                return true
            }
        }
        return false
    }

    private fun save() {
        val settings = context.getSharedPreferences("MY_LIST", Context.MODE_PRIVATE)
        val editor = settings.edit()
        val json = convertToJson(gistsList)
        editor.putString("gist_list", json)
        editor.commit()
    }

    companion object : SingletonHolder<FavoritesManager, Context>(::FavoritesManager)
}