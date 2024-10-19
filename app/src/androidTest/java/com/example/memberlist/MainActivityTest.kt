package com.example.memberlist

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.memberlist.model.Member
import com.example.memberlist.ui.main.MainActivity
import com.example.memberlist.ui.webview.WebViewActivity
import com.google.gson.Gson
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        val sharedPreferences = context.getSharedPreferences("member_list", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    @Test
    fun testAddMember() {
        onView(withId(R.id.add_button)).perform(click())

        onView(withId(R.id.member_name_input)).perform(replaceText("田中 太郎"))
        onView(withId(R.id.member_age_input)).perform(replaceText("30"))
        onView(withId(R.id.member_url_input)).perform(replaceText("https://example.com"))

        onView(withId(R.id.save_button)).perform(click())

        onView(withText("田中 太郎")).check(matches(isDisplayed()))
        onView(withText("30歳")).check(matches(isDisplayed()))
        onView(withText("https://example.com")).check(matches(isDisplayed()))
    }

    @Test
    fun testDeleteMember() {
        val member = Member(1, "田中 太郎", 30, "https://example.com")
        val memberList = listOf(member)
        val sharedPreferences = context.getSharedPreferences("member_list", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("member_list", Gson().toJson(memberList)).apply()

        activityRule.scenario.recreate()

        onView(withText("田中 太郎")).check(matches(isDisplayed()))

        onView(withId(R.id.delete_button)).perform(click())

        onView(withId(R.id.list_view)).check(matches(not(hasDescendant(withText("田中 太郎")))))
    }

    @Test
    fun testEditMember() {
        val memberToEdit = Member(1, "田中 太郎", 30, "https://example.com")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPreferences = context.getSharedPreferences("member_list", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("member_list", Gson().toJson(listOf(memberToEdit)))
        editor.apply()

        activityRule.scenario.recreate()

        onView(withText("田中 太郎")).perform(click())

        onView(withId(R.id.member_name_input)).perform(replaceText("田中 花子"))
        onView(withId(R.id.member_age_input)).perform(replaceText("25"))
        onView(withId(R.id.member_url_input)).perform(replaceText("https://example.org"))

        onView(withId(R.id.save_button)).perform(click())

        onView(withText("田中 花子")).check(matches(isDisplayed()))
        onView(withText("25歳")).check(matches(isDisplayed()))
        onView(withText("https://example.org")).check(matches(isDisplayed()))
    }

    @Test
    fun testOpenWebView() {
        Intents.init()

        val memberWithUrl = Member(1, "田中 太郎", 30, "https://example.com")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPreferences = context.getSharedPreferences("member_list", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("member_list", Gson().toJson(listOf(memberWithUrl)))
        editor.apply()

        activityRule.scenario.recreate()

        onView(withText("https://example.com")).perform(click())

        Intents.intended(hasComponent(WebViewActivity::class.java.name))
        Intents.intended(hasExtra("url", "https://example.com"))
    }
}