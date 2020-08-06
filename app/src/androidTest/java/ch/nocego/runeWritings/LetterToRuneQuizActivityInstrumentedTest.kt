package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.letterToRuneQuiz.LetterToRuneQuizActivity
import ch.nocego.runeWritings.model.Alphabet
import ch.nocego.runeWritings.model.LetterToRunes
import ch.nocego.runeWritings.model.runeInstances.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LetterToRuneQuizActivityInstrumentedtests {
    lateinit var preferencesEditor: SharedPreferences.Editor
    var targetContext: Context = getInstrumentation().targetContext
    private val ltr = LetterToRunes()

    @get:Rule
    var mainActivityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    @get: Rule
    var letterToRuneQuizActivityRule: ActivityTestRule<LetterToRuneQuizActivity> =
        ActivityTestRule(LetterToRuneQuizActivity::class.java, true, false)

    @Before
    fun setUp() {
        ContextHolder.setContext(targetContext)
        setSharedPrefsValues()
    }

    @Test
    fun lettersInUseRunesSwitchByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonLetterToRuneQuiz()
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(R.string.useRunes)))
    }

    @Test
    fun runesInUseRunesSwitchAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonLetterToRuneQuiz()
        val runicTranspilation = getRunicTranspilation(R.string.useRunes)
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInUseRunesSwitchByDefault() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(R.string.useRunes)))
    }

    @Test
    fun runesInUseRunesSwitchAfterSwitching() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.useRunes)
        onView(withId(R.id.useRunesSwitch)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun useRunesSwitchOffByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonLetterToRuneQuiz()
        onView(withId(R.id.useRunesSwitch)).check(matches(isNotChecked()))
    }

    @Test
    fun useRunesSwitchOnAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonLetterToRuneQuiz()
        onView(withId(R.id.useRunesSwitch)).check(matches(isChecked()))
    }

    @Test
    fun allLettersOnce() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)

        Alphabet.alphabetUpperCase().map {
            checkLetter(it)
        }
    }

    @Test
    fun letterToRuneQuizRuneRuneIsRedAfterFailedAttempt() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)
        onView(withId(R.id.letterToRuneQuizRune)).check(
            matches(
                editTextHasTextColor(
                    Color.parseColor(
                        "#000000"
                    )
                )
            )
        )
        onView(withId(R.id.button_berkanan)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.letterToRuneQuizRune)).check(
            matches(
                editTextHasTextColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
            )
        )
    }

    @Test
    fun letterToRuneQuizRuneUnderlineIsRedAfterFailedAttempt() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)
        onView(withId(R.id.letterToRuneQuizRune)).check(
            matches(
                editTextHasBackgroundTintColor(
                    Color.parseColor(
                        "#000000"
                    )
                )
            )
        )
        onView(withId(R.id.button_berkanan)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.letterToRuneQuizRune)).check(
            matches(
                editTextHasBackgroundTintColor(
                    Color.parseColor(
                        "#ff0000"
                    )
                )
            )
        )
    }

    @Test
    fun letterToRuneQuizRuneRuneIsBlackAfterCorrectingFailedAttempt() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)
        onView(withId(R.id.button_berkanan)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.button_delete)).perform(click())
        onView(withId(R.id.button_ansuz)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.letterToRuneQuizRune)).check(
            matches(
                editTextHasTextColor(
                    Color.parseColor(
                        "#000000"
                    )
                )
            )
        )
    }

    @Test
    fun letterToRuneQuizRuneUnderlineIsBlackAfterCorrectingFailedAttempt() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)
        onView(withId(R.id.button_berkanan)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.button_delete)).perform(click())
        onView(withId(R.id.button_ansuz)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.letterToRuneQuizRune)).check(
            matches(
                editTextHasBackgroundTintColor(
                    Color.parseColor(
                        "#000000"
                    )
                )
            )
        )
    }

    @Test
    fun correctRunesInARowCounterHasZeroByDefault() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        onView(withId(R.id.correctRunesInARowCounter)).check(matches(withText("0")))
    }

    @Test
    fun correctRunesInARowCounterCountsUp() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)

        Alphabet.alphabetUpperCase().map {
            checkLetter(it)
        }

        onView(withId(R.id.correctRunesInARowCounter)).check(matches(withText("26")))
    }

    @Test
    fun correctRunesInARowCounterResetsAfterFail() {
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)

        onView(withId(R.id.button_ansuz)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.correctRunesInARowCounter)).check(matches(withText("1")))
        onView(withId(R.id.button_algiz)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.correctRunesInARowCounter)).check(matches(withText("0")))
    }

    @Test
    fun bestSoFarHasZeroByDefault() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        onView(withId(R.id.bestSoFar)).check(matches(withText("0")))
    }

    @Test
    fun bestSoFarReadsValueFromSharedPrefs() {
        setSharedPrefsValues(bestLetterToRuneQuiz = 5)
        letterToRuneQuizActivityRule.launchActivity(Intent())
        onView(withId(R.id.bestSoFar)).check(matches(withText("5")))
    }

    @Test
    fun bestSoFarIsUpdatedWithNewBestAfterFail() {
        setSharedPrefsValues(bestLetterToRuneQuiz = 2)
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)

        onView(withId(R.id.bestSoFar)).check(matches(withText("2")))
        repeat(3) {
            pressCorrectLetterAndEnter()
        }
        onView(withId(R.id.button_algiz)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())

        onView(withId(R.id.bestSoFar)).check(matches(withText("3")))
    }

    @Test
    fun bestSoFarIsNotUpdatedIfNotNewBestAfterFail() {
        setSharedPrefsValues(bestLetterToRuneQuiz = 2)
        val intent = Intent()
        intent.putExtra("letterIndex", 0)
        letterToRuneQuizActivityRule.launchActivity(intent)

        onView(withId(R.id.bestSoFar)).check(matches(withText("2")))
        pressCorrectLetterAndEnter()
        onView(withId(R.id.button_algiz)).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.bestSoFar)).check(matches(withText("2")))
    }

    @Test
    fun bestSoFarIsUpdatedWithNewBestOnBack() {
        setSharedPrefsValues(bestLetterToRuneQuiz = 2)
        mainActivityRule.launchActivity(Intent())
        pressButtonLetterToRuneQuiz()

        onView(withId(R.id.bestSoFar)).check(matches(withText("2")))
        repeat(3) {
            pressCorrectLetterAndEnter()
        }
        Espresso.pressBack()
        pressButtonLetterToRuneQuiz()
        onView(withId(R.id.bestSoFar)).check(matches(withText("3")))
    }

    @Test
    fun lettersInCorrectRunesInARowByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonLetterToRuneQuiz()
        onView(withId(R.id.correctRunesInARow)).check(matches(withText(R.string.correctRunesInARow)))
    }

    @Test
    fun runesInCorrectRunesInARowAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonLetterToRuneQuiz()
        val runicTranspilation = getRunicTranspilation(R.string.correctRunesInARow)
        onView(withId(R.id.correctRunesInARow)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInCorrectRunesInARowByDefault() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        onView(withId(R.id.correctRunesInARow)).check(matches(withText(R.string.correctRunesInARow)))
    }

    @Test
    fun runesInCorrectRunesInARowAfterSwitching() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.correctRunesInARow)
        onView(withId(R.id.correctRunesInARow)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInBestSoFarByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonLetterToRuneQuiz()
        onView(withId(R.id.best)).check(matches(withText(R.string.best)))
    }

    @Test
    fun runesInBestSoFarAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonLetterToRuneQuiz()
        val runicTranspilation = getRunicTranspilation(R.string.best)
        onView(withId(R.id.best)).check(matches(withText(runicTranspilation)))
    }

    @Test
    fun lettersInBestSoFarByDefault() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        onView(withId(R.id.best)).check(matches(withText(R.string.best)))
    }

    @Test
    fun runesInBestSoFarAfterSwitching() {
        letterToRuneQuizActivityRule.launchActivity(Intent())
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.best)
        onView(withId(R.id.best)).check(matches(withText(runicTranspilation)))
    }

    private fun setSharedPrefsValues(useRunes: Boolean = false, bestLetterToRuneQuiz: Int = 0) {
        val sharedPrefs: SharedPreferences =
            targetContext.getSharedPreferences("RUNES_INSTED_OF_LETTERS", Context.MODE_PRIVATE)
        preferencesEditor = sharedPrefs.edit()
        preferencesEditor.putBoolean("useRunes", useRunes)
        preferencesEditor.putInt("bestLetterToRuneQuiz", bestLetterToRuneQuiz)
        preferencesEditor.commit()
    }

    private fun pressButtonLetterToRuneQuiz() {
        onView(withId(R.id.buttonletterToRuneQuiz)).perform(click())
    }

    private fun moveSwitch() {
        onView(withId(R.id.useRunesSwitch)).perform(click())
    }

    private fun getRunicTranspilation(resourceId: Int): String {
        val resourceString = targetContext.getString(resourceId)
        return ltr.getRunesFromText(resourceString)
    }

    private fun checkLetter(letter: String) {
        Log.v("letter", letter)
        onView(withId(R.id.letterToRuneQuizLetter)).check(matches(withText(letter)))
        onView(withId(runeButtonIdByString(letter))).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
        onView(withId(R.id.letterToRuneQuizRune)).check(matches(withText("")))
    }

    private fun runeButtonIdByString(letter: String): Int {
        var id: Int? = null
        val runeName: String = ltr.getRuneInstanceOfOneChar(letter.toCharArray()[0])!!.name()
        when (runeName) {
            Algiz().name() -> id = R.id.button_algiz
            Ansuz().name() -> id = R.id.button_ansuz
            Berkanan().name() -> id = R.id.button_berkanan
            Dagaz().name() -> id = R.id.button_dagaz
            Ehwaz().name() -> id = R.id.button_ehwaz
            Eihwaz().name() -> id = R.id.button_eihwaz
            Fehu().name() -> id = R.id.button_fehu
            Gyfu().name() -> id = R.id.button_gyfu
            Haglaz().name() -> id = R.id.button_haglaz
            Isaz().name() -> id = R.id.button_isaz
            Jeran(0).name() -> id = R.id.button_jeran
            Kaun(0).name() -> id = R.id.button_kaun
            Laguz().name() -> id = R.id.button_laguz
            Mannaz().name() -> id = R.id.button_mannaz
            Naudiz().name() -> id = R.id.button_naudiz
            Odal().name() -> id = R.id.button_odal
            Peord().name() -> id = R.id.button_peord
            Raido().name() -> id = R.id.button_raido
            Sigel().name() -> id = R.id.button_sigel
            Tiwaz().name() -> id = R.id.button_tiwaz
            Ur().name() -> id = R.id.button_ur
            Wynn(0).name() -> id = R.id.button_wynn
        }
        return id!!
    }

    private fun editTextHasTextColor(textColor: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("editText has TextColor $textColor")
            }

            override fun matchesSafely(item: View?): Boolean {
                val editText: EditText =
                    (item as EditText)
                val matches: Boolean = editText.currentTextColor == textColor
                return matches
            }

        }
    }

    private fun editTextHasBackgroundTintColor(textColor: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("editText has TextColor $textColor")
            }

            override fun matchesSafely(item: View?): Boolean {
                val editText: EditText =
                    (item as EditText)
                val matches: Boolean = editText.backgroundTintList!!.defaultColor == textColor
                return matches
            }

        }
    }

    fun pressCorrectLetterAndEnter() {
        val letterToRuneQuizLetter: ViewInteraction = onView(withId(R.id.letterToRuneQuizLetter))
        val letter = getText(letterToRuneQuizLetter)
        onView(withId(runeButtonIdByString(letter))).perform(click())
        onView(withId(R.id.button_enter)).perform(click())
    }

    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
        return text
    }
}