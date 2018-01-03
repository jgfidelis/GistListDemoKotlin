package com.jgfidelis.gistdemoapplication.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgfidelis.gistdemoapplication.R


/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

}// Required empty public constructor
