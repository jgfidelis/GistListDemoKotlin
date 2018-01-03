package com.jgfidelis.gistdemoapplication.fragments.fileDetails


import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.jgfidelis.gistdemoapplication.R
import com.jgfidelis.gistdemoapplication.convertToJson
import com.jgfidelis.gistdemoapplication.models.FileModelObject
import kotlinx.android.synthetic.main.fragment_file_detail.*

class FileDetailFragment : Fragment() {

    private val presenter = FileDetailFragmentPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onActivityCreated()
        layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showScrollView() {
        ScrollView01.alpha = 1f
    }

    fun hideScrollView() {
        ScrollView01.alpha = 0f
    }

    fun startAnimation() {
        activity.runOnUiThread {
            hideProgressBar()
            showScrollView()
            val myLayout = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT)
            ScrollView01.layoutParams = myLayout
            ScrollView01.requestLayout()
        }
    }

    fun setTextviewContent(result:String) {
        activity.runOnUiThread {
            mytext.text = result
        }
    }

    companion object {
        fun newInstance(file: FileModelObject): FileDetailFragment {
            val fragment = FileDetailFragment()
            val args = Bundle()
            val fileJson: String = convertToJson(file)
            args.putString("file", fileJson)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
