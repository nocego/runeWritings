package ch.nocego.runeWritings

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedtests {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun lettersInTitleByDefault() {
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(R.string.title)))
    }

    @Test
    fun lettersInDescriptionByDefault() {
        onView(withId(R.id.description)).check(matches(withText(R.string.appDescription)))
    }

    @Test
    fun lettersInSwitchRunesByDefault() {
        onView(withId(R.id.switchRunes)).check(matches(withText(R.string.useRunesInsteadOfLetters)))
    }

    @Test
    fun runesInTitleAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(R.string.titleRunic)))
    }

    private fun moveSwitch() {
        onView(withId(R.id.switchRunes)).perform(click())
    }
}
