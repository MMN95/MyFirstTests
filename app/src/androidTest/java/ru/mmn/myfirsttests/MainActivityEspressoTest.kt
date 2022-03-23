package ru.mmn.myfirsttests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.viewbinding.BuildConfig.BUILD_TYPE
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mmn.myfirsttests.view.details.DetailsActivity
import ru.mmn.myfirsttests.view.search.MainActivity

class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_IsWorking(){

        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"),
            closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        if (BUILD_TYPE == MainActivity.FAKE) {
            onView(withId(R.id.totalCountTextView))
                .check(matches(withText("Number of results: 42")))
        } else {
            onView(isRoot()).perform(delay())
            onView(withId(R.id.totalCountTextView))
                .check(matches(withText("Number of results: 2948")))
        }
    }

    @Test
    fun toDetailsButton_IsDisplayed(){
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isDisplayed()))
    }

    @Test
    fun toDetailsButtonText_IsVisible(){
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withText(R.string.to_details)))
    }

    @Test
    fun searchEditText_IsDisplayed(){
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()))
    }

    @Test
    fun searchEditTextHint_IsVisible(){
        onView(withId(R.id.searchEditText)).check(matches(withHint(R.string.search_hint)))
    }

    @Test
    fun toDetailsButton_IsWorking(){
        onView(withId(R.id.toDetailsActivityButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(matches(isDisplayed()))
    }

    @Test
    fun searchEditText_IsWorking(){
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("hello"),
            closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).check(matches(withText("hello")))
    }

    @After
    fun close() {
        scenario.close()
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }


}