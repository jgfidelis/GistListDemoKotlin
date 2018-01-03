package com.jgfidelis.gistdemoapplication

import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jgfidelis.gistdemoapplication.adapters.RecyclerCustomAdapter
import com.jgfidelis.gistdemoapplication.adapters.SectionsPagerAdapter
import com.jgfidelis.gistdemoapplication.fragments.AboutFragment
import com.jgfidelis.gistdemoapplication.fragments.favorite.FavoritesFragment
import com.jgfidelis.gistdemoapplication.fragments.home.HomeFragment
import org.hamcrest.Matchers.allOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by jgfidelis on 25/12/17.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testSectionsPagerAdapterConstruction() {
        val adapter = SectionsPagerAdapter(activityTestRule.activity.supportFragmentManager)
        Assert.assertNotNull(adapter)

        assert(adapter.getItem(0) is HomeFragment)
        assert(adapter.getItem(1) is FavoritesFragment)
        assert(adapter.getItem(2) is AboutFragment)
    }

    @Test
    fun testAppFlowHome() {
        SystemClock.sleep(3000)
        val viewAction = RecyclerViewActions.actionOnItemAtPosition<RecyclerCustomAdapter.ViewHolder>(0, click())
        onView(withId(R.id.recyclerViewHome)).perform(viewAction)
        onView(withId(R.id.recyclerViewGist)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerViewGist)).perform(viewAction)
        SystemClock.sleep(3000)
        onView(withId(R.id.mytext)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.recyclerViewGist)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.recyclerViewHome)).check(matches(isDisplayed()))
    }

    @Test
    fun testAppFlowFavorites() {
        SystemClock.sleep(3000)
        val viewAction = RecyclerViewActions.actionOnItemAtPosition<RecyclerCustomAdapter.ViewHolder>(0, click())
        onView(ViewMatchers.withId(R.id.recyclerViewHome)).perform(viewAction)
        onView(withId(R.id.button)).perform(click()) //saving favorite
        pressBack()

        val matcher = allOf(withText("Favorites"),
                isDescendantOfA(withId(R.id.tabs)))
        onView(matcher).perform(click())

        onView(withId(R.id.recyclerViewFavs)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerViewFavs)).perform(viewAction)
        onView(withId(R.id.recyclerViewGist)).check(matches(isDisplayed()))
        onView(withId(R.id.button)).perform(click()) //removing favorite
        onView(withId(R.id.recyclerViewGist)).perform(viewAction)
        SystemClock.sleep(3000)
        onView(withId(R.id.mytext)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.recyclerViewGist)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.message)).check(matches(isDisplayed()))
    }

    @Test
    fun testAbout() {
        SystemClock.sleep(1000)
        val matcher = allOf(withText("About"),
                isDescendantOfA(withId(R.id.tabs)))
        onView(matcher).perform(click())
        onView(withId(R.id.aboutTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.aboutMessage)).check(matches(isDisplayed()))
    }
}