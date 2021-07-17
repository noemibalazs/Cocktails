package com.noemi.android.cocktails

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

class EspressoHelper {

    companion object {

        fun waitFor(milliSeconds: Long): ViewAction {
            return object : ViewAction {
                override fun getDescription(): String {
                    return "Wait for $milliSeconds"
                }

                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun perform(uiController: UiController?, view: View?) {
                    uiController?.loopMainThreadForAtLeast(milliSeconds)
                }
            }
        }
    }
}