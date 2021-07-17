package com.noemi.android.cocktails

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.noemi.android.cocktails.landing.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityInstrumentationTest {

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    @Test
    fun testAppContext() {
        val context = InstrumentationRegistry.getInstrumentation().context
        Assert.assertEquals("com.noemi.android.cocktails.test", context.packageName)
    }

    @Test
    fun testBusinessLogic() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(R.id.flContainer)).check(matches(isDisplayed()))
    }
}