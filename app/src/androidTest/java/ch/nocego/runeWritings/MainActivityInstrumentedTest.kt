package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import ch.nocego.runeWritings.model.LetterToRunes
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedtests {
    lateinit var preferencesEditor: SharedPreferences.Editor
    val ltr = LetterToRunes()
    var targetContext: Context = getInstrumentation().targetContext

    @get:Rule
    var mainActivityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        setDefaultSharedPrefsValues()
        mainActivityRule.launchActivity(Intent())
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
        onView(withId(R.id.buttonRunicAlphabet)).check(matches(withText(R.string.runicAlphabet)))
    }

    @Test
    fun lettersInButtonRunicAlphabetByDefault() {
        onView(withId(R.id.buttonRunicAlphabet)).check(matches(withText(R.string.runicAlphabet)))
    }

    @Test
    fun runesInUseRunesSwitchAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.useRunes)
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInActivityTitleAfterSwitching() {
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
    fun runesInButtonGenerateUnicodeRunesAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.buttonRunicAlphabet)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun runesInButtonRunicAlphabetAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.buttonRunicAlphabet)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun activityTitleTranspiledToRunesByOnResumeFromRunicAlphabetActivityIfChanged() {
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        val runicTranspilation = getRunicTranspilation(R.string.title)
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun activityTitleNotTranspiledToRunesByOnResumeFromRunicAlphabetActivityIfChanged() {
        moveSwitch()
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        onView(withId(R.id.mainActivityTitle)).check(matches(withText(R.string.title)))
    }

    @Test
    fun useRunesSwitchCheckedByOnResumeFromRunicAlphabetActivityIfChanged() {
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        onView(withId(R.id.useRunesSwitch)).check(matches(isChecked()))
    }

    @Test
    fun useRunesNotSwitchCheckedByOnResumeFromRunicAlphabetActivityIfChanged() {
        moveSwitch()
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        onView(withId(R.id.useRunesSwitch)).check(matches(isNotChecked()))
    }

    @Test
    fun lettersInCopyrightByDefault() {
        onView(withId(R.id.copyright)).check(matches(withText(R.string.copyright)))
    }

    @Test
    fun runesInCopyrightAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.copyright)
        onView(withId(R.id.copyright)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun copyrightTranspiledToRunesByOnResumeFromRunicAlphabetActivityIfChanged() {
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        val runicTranspilation = getRunicTranspilation(R.string.copyright)
        onView(withId(R.id.copyright)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun copyrightNotTranspiledToRunesByOnResumeFromRunicAlphabetActivityIfChanged() {
        moveSwitch()
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        onView(withId(R.id.copyright)).check(matches(withText(R.string.copyright)))
    }

    @Test
    fun lettersInTranspilationNoteByDefault() {
        onView(withId(R.id.transpilationNote)).check(matches(withText(R.string.transpilationNote)))
    }

    @Test
    fun runesInTranspilationNoteAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.transpilationNote)
        onView(withId(R.id.transpilationNote)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun transpilationNoteTranspiledToRunesByOnResumeFromRunicAlphabetActivityIfChanged() {
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        val runicTranspilation = getRunicTranspilation(R.string.transpilationNote)
        onView(withId(R.id.transpilationNote)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun transpilationNoteNotTranspiledToRunesByOnResumeFromRunicAlphabetActivityIfChanged() {
        moveSwitch()
        onView(withId(R.id.buttonRunicAlphabet)).perform(click())
        moveSwitch()
        pressBack()
        onView(withId(R.id.transpilationNote)).check(matches(withText(R.string.transpilationNote)))
    }

    @Test
    fun lettersInButtonLetterToRunesByDefault() {
        onView(withId(R.id.buttonletterToRuneQuiz)).check(matches(withText(R.string.letterToRuneQuiz)))
    }

    @Test
    fun runesInButtonLetterToRunesAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.letterToRuneQuiz)
        onView(withId(R.id.buttonletterToRuneQuiz)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInButtonRunesToLetterByDefault() {
        onView(withId(R.id.buttonRuneToLetterQuiz)).check(matches(withText(R.string.runeToLetterQuiz)))
    }

    @Test
    fun runesInButtonRunesToLetterAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.runeToLetterQuiz)
        onView(withId(R.id.buttonRuneToLetterQuiz)).check(matches(withText(runicTranspilation)))
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
