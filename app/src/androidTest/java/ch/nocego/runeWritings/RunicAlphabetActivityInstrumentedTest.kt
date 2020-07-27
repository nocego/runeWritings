package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.LetterToRunes
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RunicAlphabetActivityInstrumentedtests {
    lateinit var preferencesEditor: SharedPreferences.Editor
    val ltr = LetterToRunes()
    var targetContext: Context = getInstrumentation().targetContext

    @get:Rule
    var mainActivityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    @get: Rule
    var runicAlphabetActivityRule: ActivityTestRule<RunicAlphabetActivity> =
        ActivityTestRule(RunicAlphabetActivity::class.java, true, false)

    @Before
    fun setUp() {
        ContextHolder.setContext(targetContext)
        setDefaultSharedPrefsValues()
        runicAlphabetActivityRule.launchActivity(Intent())
    }


    @Test
    fun lettersInUseRunesSwitchByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(R.string.useRunes)))
    }

    @Test
    fun runesInUseRunesSwitchAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        val runicTranspilation = getRunicTranspilation(R.string.useRunes)
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInUseRunesSwitchByDefault() {
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(R.string.useRunes)))
    }

    @Test
    fun runesInUseRunesSwitchAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.useRunes)
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun useRunesSwitchOffByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.useRunesSwitch)).check(matches(isNotChecked()))
    }

    @Test
    fun useRunesSwitchOnAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        onView(withId(R.id.useRunesSwitch)).check(matches(isChecked()))
    }

    @Test
    fun lettersInRunicAlphabetActivityTitleByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.runicAlphabetActivityTitle)).check(matches(withText(R.string.runicAlphabet)))
    }

    @Test
    fun runesInRunicAlphabetActivityTitleAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.runicAlphabetActivityTitle)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInRunicAlphabetActivityTitleByDefault() {
        onView(withId(R.id.runicAlphabetActivityTitle)).check(matches(withText(R.string.runicAlphabet)))
    }

    @Test
    fun runesInRunicAlphabetActivityTitleAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.runicAlphabetActivityTitle)).check(matches(withText(runicTranspilation)))
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

    private fun pressButtonRunicAlphabet() {
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
    }

    private fun getRunicTranspilation(resourceId: Int): String {
        val resourceString = targetContext.getString(resourceId)
        return ltr.getRunesFromText(resourceString)
    }
}
