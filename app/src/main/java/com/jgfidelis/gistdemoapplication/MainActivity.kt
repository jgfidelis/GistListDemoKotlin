package com.jgfidelis.gistdemoapplication

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.jgfidelis.gistdemoapplication.adapters.SectionsPagerAdapter
import com.jgfidelis.gistdemoapplication.utils.FavoritesManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        container.offscreenPageLimit = 2
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        FavoritesManager.getInstance(this)
        container.addOnPageChangeListener(mSectionsPagerAdapter!!)
    }

    fun switchContent(position: Int, fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.remove(mSectionsPagerAdapter?.getItem(position)).commit()
        mSectionsPagerAdapter?.switchFragment(position, fragment)
    }

    override fun onBackPressed() {
        if (mSectionsPagerAdapter!!.shouldUseParentBackAction()) {
            return super.onBackPressed()
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.remove(mSectionsPagerAdapter?.getCurrentItem()).commit()
        mSectionsPagerAdapter?.backPressed()
    }
}
