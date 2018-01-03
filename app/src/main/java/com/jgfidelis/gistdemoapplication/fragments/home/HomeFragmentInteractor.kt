package com.jgfidelis.gistdemoapplication.fragments.home

import com.jgfidelis.gistdemoapplication.models.GistModelObject
import com.jgfidelis.gistdemoapplication.utils.HttpRequestCallback
import com.jgfidelis.gistdemoapplication.utils.HttpRequestHelper
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by jgfidelis on 23/12/17.
 */

class HomeFragmentInteractor(presenter: HomeFragmentPresenter) {
    private val presenter = presenter
    private var isLoadingPage = false

    fun loadPage(page:Int) {
        if (isLoadingPage) return
        isLoadingPage = true
        val callback = object: HttpRequestCallback {
            override fun onSuccess(data: String) {
                val gistList = parse(data)
                presenter.updateGistList(gistList, page)
                isLoadingPage = false
            }
            override fun onFailure(status: Int) {
                presenter.showErrorDialog()
                isLoadingPage = false
            }
        }
        HttpRequestHelper.requestGistList(page, callback)
    }

    fun parse(json: String):ArrayList<GistModelObject> {
        val jsonArray = JSONArray(json)
        val gistList = ArrayList<GistModelObject>()
        for (i in 0..(jsonArray.length() - 1)) {
            val o = JSONObject(jsonArray.optString(i))
            val gist = GistModelObject.parse(o, presenter.getContext())
            gistList.add(gist)
        }
        return gistList
    }
}