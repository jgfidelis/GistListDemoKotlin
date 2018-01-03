package com.jgfidelis.gistdemoapplication.fragments.fileDetails

import com.jgfidelis.gistdemoapplication.utils.HttpRequestCallback
import com.jgfidelis.gistdemoapplication.utils.HttpRequestHelper

/**
 * Created by jgfidelis on 23/12/17.
 */
class FileDetailFragmentInteractor(presenter: FileDetailFragmentPresenter) {
    private val presenter = presenter

    fun loadContent(url:String) {
        val callback = object : HttpRequestCallback {
            override fun onSuccess(data: String) {
                presenter.contentLoaded(data)
            }

            override fun onFailure(status: Int) {
                presenter.showErrorDialog()
            }
        }
        HttpRequestHelper.requestFileContent(url, callback)
    }
}