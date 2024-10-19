package com.example.memberlist

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memberlist.ui.webview.WebViewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebViewActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule<WebViewActivity>(
        Intent(
            ApplicationProvider.getApplicationContext(),
            WebViewActivity::class.java
        ).apply {
            putExtra("url", "https://www.example.com")
        })

    @Test
    fun testWebViewLoads() {
        onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testCloseButton() {
        onView(withId(R.id.close_button)).perform(click())
        
        assert(activityRule.scenario.state.isAtLeast(Lifecycle.State.DESTROYED))
    }

    @Test
    fun testTitleUpdates() {
        onView(withId(R.id.title_text_view)).check(matches(withText("Example Domain")))
    }
}