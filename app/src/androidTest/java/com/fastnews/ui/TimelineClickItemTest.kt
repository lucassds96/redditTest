package com.fastnews.ui

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fastnews.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TimelineClickItemTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(SchemeActivity::class.java)

    @Test
    fun timelineClickItemTest() {
        Thread.sleep(6000)

        val cardView = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.timeline_rv),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        cardView.perform(ViewActions.click())

        val linearLayout = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.detail_post_container),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.bottomsheet),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        linearLayout.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
