package com.jgfidelis.gistdemoapplication.fragments.fileDetails

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.models.FileModelObject

/**
 * Created by jgfidelis on 23/12/17.
 */

class FileDetailFragmentPresenter(frag: FileDetailFragment) {
    private val frag = frag
    private var file:FileModelObject? = null
    private val interactor = FileDetailFragmentInteractor(this)

    fun onActivityCreated() {
        frag.hideScrollView()
        frag.showProgressBar()
        val json = frag.arguments.getString("file")
        file = FileModelObject.convertJsonToFile(json)
        interactor.loadContent(file!!.fileUrl)
    }

    fun contentLoaded(result:String) {
        frag.setTextviewContent(result)
        frag.startAnimation()
    }

    fun showErrorDialog() {
        val builder = AlertDialog.Builder(frag.activity)
        builder.setMessage(frag.resources.getString(R.string.connection_error))
        builder.setCancelable(true)

        builder.setPositiveButton(frag.resources.getString(R.string.yes), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                interactor.loadContent(file!!.fileUrl)
                dialog?.cancel()
            }
        })

        builder.setNegativeButton(
                frag.resources.getString(R.string.no)
        ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
    }
}