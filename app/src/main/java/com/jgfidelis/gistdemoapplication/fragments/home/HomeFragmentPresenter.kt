package com.jgfidelis.gistdemoapplication.fragments.home

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.convertToJson
import com.jgfidelis.gistdemoapplication.models.GistModelObject

/**
 * Created by jgfidelis on 23/12/17.
 */
class HomeFragmentPresenter(frag: HomeFragment) {
    private val frag: HomeFragment = frag
    private val interactor: HomeFragmentInteractor = HomeFragmentInteractor(this)
    private var currentPageLoaded:Int = 0
    private var gists:ArrayList<GistModelObject> = ArrayList<GistModelObject>()

    fun onActivityCreated() {
        frag.setAdapterWithList(gists)
        frag.addSrollListener()
    }

    fun fetchNextPage() {
        frag.warnAdapterAboutUpdate()
        interactor.loadPage(currentPageLoaded+1)
    }

    fun updateGistList(gistList:List<GistModelObject>, page:Int) {
        gists.addAll(gistList)
        frag.updateGistList(gists)
        currentPageLoaded = page
    }

    fun convertListToJson(): String {
        return convertToJson(gists)
    }

    fun showErrorDialog() {
        frag.warnAdapterUpdateFailed()
        val builder = AlertDialog.Builder(frag.activity)
        builder.setMessage(frag.resources.getString(R.string.connection_error))
        builder.setCancelable(true)

        builder.setPositiveButton(frag.resources.getString(R.string.yes), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                fetchNextPage()
                dialog?.cancel()
            }
        })

        builder.setNegativeButton(
                frag.resources.getString(R.string.no)
        ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
    }

    fun getContext(): Context {
        return frag.activity
    }
}