package com.example.tara.ui.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.tara.R
import com.example.tara.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun logout_Success() {
        Espresso.onView(ViewMatchers.withId(R.id.menu_profile)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.profile_activity_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.btn_logout)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.login_activity_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}