package com.jgfidelis.gistdemoapplication.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.jgfidelis.gistdemoapplication.fragments.AboutFragment
import com.jgfidelis.gistdemoapplication.fragments.favorite.FavoritesFragment
import com.jgfidelis.gistdemoapplication.fragments.fileDetails.FileDetailFragment
import com.jgfidelis.gistdemoapplication.fragments.gistDetails.GistDetailFragment
import com.jgfidelis.gistdemoapplication.fragments.home.HomeFragment

class SectionsPagerAdapter : FragmentStatePagerAdapter, ViewPager.OnPageChangeListener {
    var currentPage: Int = 0
    constructor(fm: FragmentManager) : super(fm) {
        frag0List = ArrayList<Fragment>()
        frag1List = ArrayList<Fragment>()
        frag0List.add(HomeFragment.newInstance())
        frag1List.add(FavoritesFragment.newInstance())
        aboutFrag = AboutFragment.newInstance()
        this.fm = fm
    }
    private var fm: FragmentManager
    private var frag0List: ArrayList<Fragment>
    private var frag1List: ArrayList<Fragment>
    private var aboutFrag: Fragment

    override fun onPageScrollStateChanged(state: Int) {
        //do nothing
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //do nothing
    }

    override fun onPageSelected(position: Int) {
        currentPage = position
        fragmentWillShow()
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return frag0List.last()
        } else if (position == 1) {
            return frag1List.last()
        } else {
            return aboutFrag
        }
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }

    override fun getItemPosition(`object`: Any?): Int {
        println(`object`)
        if (currentPage == 0) {
            if (`object` is HomeFragment && frag0List.last() is GistDetailFragment) {
                return PagerAdapter.POSITION_NONE
            } else if (`object` is GistDetailFragment && frag0List.last() is HomeFragment) {
                return PagerAdapter.POSITION_NONE
            } else if (`object` is GistDetailFragment && frag0List.last() is FileDetailFragment) {
                return PagerAdapter.POSITION_NONE
            } else if (`object` is FileDetailFragment && frag0List.last() is GistDetailFragment) {
                return PagerAdapter.POSITION_NONE
            } else {
                return PagerAdapter.POSITION_UNCHANGED
            }
        } else if (currentPage == 1) {
            if (`object` is FavoritesFragment && frag1List.last() is GistDetailFragment) {
                return PagerAdapter.POSITION_NONE
            } else if (`object` is GistDetailFragment && frag1List.last() is FavoritesFragment) {
                return PagerAdapter.POSITION_NONE
            } else if (`object` is GistDetailFragment && frag1List.last() is FileDetailFragment) {
                return PagerAdapter.POSITION_NONE
            } else if (`object` is FileDetailFragment && frag1List.last() is GistDetailFragment) {
                return PagerAdapter.POSITION_NONE
            } else {
                return PagerAdapter.POSITION_UNCHANGED
            }
        } else {
            return PagerAdapter.POSITION_UNCHANGED
        }
    }

    fun switchFragment(position: Int, frag: Fragment) {
        if (position == 0) {
            frag0List.add(frag)
        } else if (position == 1) {
            frag1List.add(frag)
        }
        notifyDataSetChanged()
    }

    fun backPressed() {
        if (currentPage == 0) {
            frag0List.removeAt(frag0List.size - 1)
        } else if (currentPage == 1) {
            frag1List.removeAt(frag1List.size - 1)
        }
        notifyDataSetChanged()
    }

    fun shouldUseParentBackAction(): Boolean {
        when(currentPage) {
            0 -> return (frag0List.last() is HomeFragment)
            1 -> return (frag1List.last() is FavoritesFragment)
            else -> return true
        }
    }

    fun getCurrentItem(): Fragment {
        return getItem(currentPage)
    }

    fun fragmentWillShow() {
        var fragment:Fragment?
        if (currentPage == 0) {
            fragment = frag0List.last()
        } else if (currentPage == 1) {
            fragment = frag1List.last()
        } else {
            fragment = aboutFrag
        }
        if (fragment is FavoritesFragment || fragment is GistDetailFragment) {
            fm.beginTransaction().detach(fragment).attach(fragment).commit()
        }
    }
}