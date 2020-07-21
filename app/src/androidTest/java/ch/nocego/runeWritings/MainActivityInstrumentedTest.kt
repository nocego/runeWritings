package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import ch.nocego.runeWritings.runes.LetterToRunes
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedtests {
    lateinit var preferencesEditor: SharedPreferences.Editor
    val ltr = LetterToRunes()
    var targetContext: Context = getInstrumentation().targetContext

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        setDefaultSharedPrefsValues()
        activityRule.launchActivity(Intent())
    }

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
        val runicTranspilation = ltr.getRunesFromText(targetContext.getString(R.string.title))
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInDescriptionAfterSwitching() {
        moveSwitch()
        val runicTranspilation =
            ltr.getRunesFromText(targetContext.getString(R.string.appDescription))
        onView(withId(R.id.description)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInSwitchRunesAfterSwitching() {
        moveSwitch()
        val runicTranspilation =
            ltr.getRunesFromText(targetContext.getString(R.string.useRunesInsteadOfLetters))
        onView(withId(R.id.switchRunes)).check(matches(withText(runicTranspilation)))
    }

    private fun moveSwitch() {
        onView(withId(R.id.switchRunes)).perform(click())
    }

    private fun setDefaultSharedPrefsValues() {
        val sharedPrefs: SharedPreferences =
            targetContext.getSharedPreferences("RUNES_INSTED_OF_LETTERS", Context.MODE_PRIVATE)
        preferencesEditor = sharedPrefs.edit()
        preferencesEditor.putBoolean("useRunes", false)
        preferencesEditor.commit()
    }
}
