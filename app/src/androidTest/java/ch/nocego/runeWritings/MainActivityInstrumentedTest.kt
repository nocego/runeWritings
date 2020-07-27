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
    fun lettersInUseRunesSwitchByDefault() {
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(R.string.useRunes)))
    }

    @Test
    fun lettersInActivityTitleByDefault() {
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(R.string.title)))
    }

    @Test
    fun lettersInDescriptionByDefault() {
        onView(withId(R.id.description)).check(matches(withText(R.string.appDescription)))
    }

    @Test
    fun lettersInButtonGenerateUnicodeRunesByDefault() {
        onView(withId(R.id.buttonGenerateUnicodeRunes)).check(matches(withText(R.string.generateUnicodeRunes)))
    }

    @Test
    fun lettersInButtonRunicAlphabetByDefault() {
        onView(withId(R.id.buttonRunicAlphabet)).check(matches(withText(R.string.runicAlphabet)))
    }

    @Test
    fun runesInTitleAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.title)
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInUseRunesSwitchByDefault() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.useRunes)
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInActivityTitleByDefault() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.title)
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInDescriptionAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.appDescription)
        onView(withId(R.id.description)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInButtonGenerateUnicodeRunesByDefault() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.generateUnicodeRunes)
        onView(withId(R.id.buttonGenerateUnicodeRunes)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInButtonRunicAlphabetByDefault() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.buttonRunicAlphabet)).check(matches(withText(runicTranspilation)))
    }

    private fun setDefaultSharedPrefsValues() {
        val sharedPrefs: SharedPreferences =
            targetContext.getSharedPreferences("RUNES_INSTED_OF_LETTERS", Context.MODE_PRIVATE)
        preferencesEditor = sharedPrefs.edit()
        preferencesEditor.putBoolean("useRunes", false)
        preferencesEditor.commit()
    }

    private fun moveSwitch() {
        onView(withId(R.id.useRunesSwitch)).perform(click())
    }

    private fun getRunicTranspilation(resourceId : Int) : String {
        val resourceString = targetContext.getString(resourceId)
        return ltr.getRunesFromText(resourceString)
    }


}
