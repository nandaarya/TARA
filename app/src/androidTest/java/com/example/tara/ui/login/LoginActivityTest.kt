package com.example.tara.ui.login

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tara.BuildConfig
import com.example.tara.R
import com.example.tara.ui.profile.ProfileActivity
import com.example.tara.ui.userpreferences.UserPreferencesActivity
import com.example.tara.utils.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun login_Success() {
        Espresso.onView(withId(R.id.emailEditText)).perform(
            ViewActions.typeText(BuildConfig.USERNAME),
            ViewActions.pressImeActionButton()
        )
        Espresso.onView(withId(R.id.edt_password_login)).perform(
            ViewActions.typeText(BuildConfig.PASSWORD),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.user_preferences_activity_layout))
            .check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withId(R.id.chip_pantai)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.chip_gunung)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.btn_save)).perform(ViewActions.click())
    }
}