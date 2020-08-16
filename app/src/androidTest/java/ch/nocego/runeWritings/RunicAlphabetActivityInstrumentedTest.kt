package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.model.Alphabet
import ch.nocego.runeWritings.model.LetterToRunes
import ch.nocego.runeWritings.model.Rune
import ch.nocego.runeWritings.model.Transpiler.Companion.runeUnicodeByLetter
import ch.nocego.runeWritings.runicAlphabet.RunicAlphabetActivity
import com.google.android.material.tabs.TabLayout
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.floor


@RunWith(AndroidJUnit4::class)
class RunicAlphabetActivityInstrumentedtests {
    private val ltr = LetterToRunes()
    private val alphabetItemWidthInDp = 70 //50+2*10
    private var targetContext: Context = getInstrumentation().targetContext
    private lateinit var preferencesEditor: SharedPreferences.Editor
    private lateinit var runes: MutableList<Rune>
    private lateinit var alphabetUpperCaseIterator: Iterator<String>

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
        alphabetUpperCaseIterator = Alphabet.alphabetUpperCase().iterator()
        runes = ArrayList()
        alphabetUpperCaseIterator.forEach {
            runes.add(ltr.getRuneInstanceOfOneChar(it.toCharArray()[0])!!)
        }
        alphabetUpperCaseIterator = Alphabet.alphabetUpperCase().iterator()
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
    fun tabLayoutZeroIsSelectedByDefault() {
        onView(withId(R.id.tabs)).check(matches(tabIsSelected(0)))
    }

    @Test
    fun tabLayoutOneIsSelectedAfterChange() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        onView(withId(R.id.tabs)).check(matches(tabIsSelected(1)))
    }

    @Test
    fun lettersInTabLayoutZeroByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(0, R.string.runicAlphabet)))
    }

    @Test
    fun runesInTabLayoutZeroAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(0, runicTranspilation)))
    }

    @Test
    fun lettersInTabLayoutZeroByDefault() {
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(0, R.string.runicAlphabet)))
    }

    @Test
    fun runesInTabLayoutZeroAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.runicAlphabet)
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(0, runicTranspilation)))
    }

    @Test
    fun lettersInTabLayoutOneByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(1, R.string.furtherInformation)))
    }

    @Test
    fun runesInTabLayoutOneAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        val runicTranspilation = getRunicTranspilation(R.string.furtherInformation)
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(1, runicTranspilation)))
    }

    @Test
    fun lettersInTabLayoutOneByDefault() {
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(1, R.string.furtherInformation)))
    }

    @Test
    fun runesInTabLayoutOneAfterSwitching() {
        moveSwitch()
        val runicTranspilation = getRunicTranspilation(R.string.furtherInformation)
        onView(withId(R.id.tabs)).check(matches(tabMatchesWithText(1, runicTranspilation)))
    }

    @Test
    fun correctLettersInFirstColumnByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)

        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(Alphabet.alphabetUpperCase()[x], R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        Alphabet.alphabetUpperCase()[x],
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesInFirstColumnAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)

        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].unicodeSymbol(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersInFirstColumnByDefault() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(Alphabet.alphabetUpperCase()[x], R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        Alphabet.alphabetUpperCase()[x],
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesInFirstColumnAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].unicodeSymbol(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesInSecondColumnByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowRune)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].unicodeSymbol(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRune
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersInSecondColumnAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        moveSwitch()
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getUpperCaseTextFromRunes(runes[x].unicodeSymbol()),
                        R.id.rowRune
                    )
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getUpperCaseTextFromRunes(runes[x].unicodeSymbol()),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRune
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesInSecondColumnByDefault() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowRune)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].unicodeSymbol(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRune
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersInSecondColumnAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getUpperCaseTextFromRunes(runes[x].unicodeSymbol()),
                        R.id.rowRune
                    )
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getUpperCaseTextFromRunes(runes[x].unicodeSymbol()),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRune
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersStringRowRuneNameByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].name(), R.id.rowRuneName)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].name(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRuneName
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesStringRowRuneNameAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        Thread.sleep(500)
        moveSwitch()
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].name()),
                        R.id.rowRuneName
                    )
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].name()),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRuneName
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersStringRowRuneNameByDefault() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].name(), R.id.rowRuneName)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].name(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRuneName
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesStringRowRuneNameAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].name()),
                        R.id.rowRuneName
                    )
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].name()),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowRuneName
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersStringRowDescriptionTextByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].description(), R.id.rowDescriptionText)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].description(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowDescriptionText
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesStringRowDescriptionTextAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        Thread.sleep(500)
        moveSwitch()
        pressButtonRunicAlphabet()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].description()),
                        R.id.rowDescriptionText
                    )
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].description()),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowDescriptionText
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersStringRowDescriptionTextByDefault() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].description(), R.id.rowDescriptionText)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].description(),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowDescriptionText
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesStringRowDescriptionTextAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)
        val listViewItemsCanBeScrolledTo = listViewItemsCanBeScrolledTo()

        for (x in 0..listViewItemsCanBeScrolledTo) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].description()),
                        R.id.rowDescriptionText
                    )
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in listViewItemsCanBeScrolledTo + 1..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].description()),
                        x % listViewItemsCanBeScrolledTo,
                        R.id.rowDescriptionText
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersAlphabetItemLetterByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithLetter(
                        it,
                        viewId = R.id.alphabetItemLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesAlphabetItemLetterAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        Thread.sleep(500)
        moveSwitch()
        pressButtonRunicAlphabet()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithRune(
                        it,
                        rune = ltr.getRunesFromText(it),
                        viewId = R.id.alphabetItemLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersAlphabetItemLetterByDefault() {
        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithLetter(
                        it,
                        viewId = R.id.alphabetItemLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctRunesAlphabetItemLetterAfterSwitching() {
        moveSwitch()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithRune(
                        it,
                        rune = ltr.getRunesFromText(it),
                        viewId = R.id.alphabetItemLetter
                    )
                )
            )
        }
    }

    @Test
    fun correctRuneAlphabetItemRuneByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithRune(
                        it,
                        rune = runeUnicodeByLetter(it),
                        viewId = R.id.alphabetItemRune
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersAlphabetItemRuneAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        Thread.sleep(500)
        moveSwitch()
        pressButtonRunicAlphabet()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithLetter(
                        ltr.getUpperCaseTextFromRunes(runeUnicodeByLetter(it)),
                        viewId = R.id.alphabetItemRune
                    )
                )
            )
        }
    }

    @Test
    fun correctRuneAlphabetItemRuneByDefault() {
        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithRune(
                        it,
                        rune = runeUnicodeByLetter(it),
                        viewId = R.id.alphabetItemRune
                    )
                )
            )
        }
    }

    @Test
    fun correctLetterAlphabetItemRuneAfterSwitching() {
        moveSwitch()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        alphabetUpperCaseIterator.forEach {
            layout.check(
                matches(
                    alphabetItemTextViewMatchesWithLetter(
                        ltr.getUpperCaseTextFromRunes(runeUnicodeByLetter(it)),
                        viewId = R.id.alphabetItemRune
                    )
                )
            )
        }
    }

    @Test
    fun rowMainHasSelectableItemBackground() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)

        onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
            matches(
                rowContainsSelectableItemBackground()
            )
        )
    }

    @Test
    fun scrollToCorrectListViewItemInFurtherRuneInformation() {
        alphabetUpperCaseIterator.forEach {
            checkClickAndScroll(it)
        }
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

    private fun tabIsSelected(tabId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("TabLayout has Index $tabId selected")
            }

            override fun matchesSafely(item: View?): Boolean {
                return (item as TabLayout).selectedTabPosition == tabId
            }
        }
    }

    private fun tabMatchesWithText(tabId: Int, matchingText: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("TabLayout with Index $tabId has $matchingText")
            }

            override fun matchesSafely(item: View?): Boolean {
                val selectedTab = (item as TabLayout).getTabAt(tabId)
                return selectedTab!!.text == matchingText
            }

        }
    }

    private fun tabMatchesWithText(tabId: Int, matchingTextId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            val matchingText = targetContext.getString(matchingTextId)

            override fun describeTo(description: Description) {
                description.appendText("TabLayout with Index $tabId has $matchingText")
            }

            override fun matchesSafely(item: View?): Boolean {
                val selectedTab = (item as TabLayout).getTabAt(tabId)
                return selectedTab!!.text == matchingText
            }

        }
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    private fun mySwipeUp(startingIndex: Int) {
        onData(anything()).atPosition(startingIndex).perform(swipeUp())
        Thread.sleep(100)
    }

    private fun mySwipeLeftOnListView() {
        onData(anything()).atPosition(0).perform(swipeRight())
        Thread.sleep(200)
    }

    private fun listViewViewContainsStringMatch(currentLetter: String, viewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("listView child at index 0 contains string $currentLetter in viewId")
            }

            override fun matchesSafely(item: View?): Boolean {
                val rowLetter: String =
                    (item as ListView).getChildAt(0).findViewById<TextView>(viewId)
                        .text.toString()
                return rowLetter == currentLetter
            }

        }
    }

    private fun rowContainsSelectableItemBackground(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("row has selectableItemBackground")
            }

            override fun matchesSafely(item: View?): Boolean {
                val row: View =
                    (item as ListView).getChildAt(0)
                return row.foreground != null
            }

        }
    }

    private fun listViewViewContainsStringMatch(
        currentLetter: String,
        listViewIndex: Int,
        viewId: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("listView child at index $listViewIndex contains string $currentLetter in viewId")
            }

            override fun matchesSafely(item: View?): Boolean {
                val rowLetter: String = (item as ListView).getChildAt(listViewIndex)
                    .findViewById<TextView>(viewId).text.toString()
                return rowLetter == currentLetter
            }

        }
    }

    private fun checkClickAndScroll(letter: String): ViewInteraction {
        onView(
            alphabetItemAt(
                letterRowIndex(letter),
                letterColumnIndex(letter)
            )
        ).perform(scrollTo(), click())
        val returnValue: ViewInteraction
        if (letterIndexInAlphabet(letter) <= listViewItemsCanBeScrolledTo()) {
            returnValue = onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        Alphabet.alphabetUpperCase()[letterIndexInAlphabet(letter)],
                        R.id.rowLetter
                    )
                )
            )
        } else {
            returnValue = onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        Alphabet.alphabetUpperCase()[letterIndexInAlphabet(letter)],
                        letterIndexInAlphabet(letter) % listViewItemsCanBeScrolledTo(),
                        R.id.rowLetter
                    )
                )
            )
        }

        mySwipeLeftOnListView()
        return returnValue
    }

    private fun alphabetItemAt(verticalIndex: Int, horizontalIndex: Int): Matcher<View?>? {
        return nthChildOf(
            nthChildOf(withId(R.id.runicAlphabetLinearScrollLayout), verticalIndex)!!,
            horizontalIndex
        )!!
    }

    private fun nthChildOf(parentMatcher: Matcher<View?>, childPosition: Int): Matcher<View?>? {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with $childPosition child view of type parentMatcher")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) {
                    return parentMatcher.matches(view.parent)
                }
                val group = view.parent as ViewGroup
                return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
            }
        }
    }

    private fun screenHeightInDp(): Float {
        val displayMetrics: DisplayMetrics = targetContext.resources.displayMetrics
        val screenHeightInDp: Float = displayMetrics.heightPixels / displayMetrics.density
        return screenHeightInDp
    }

    class ViewSizeMatcher(private val expectedWith: Int, private val expectedHeight: Int) :
        TypeSafeMatcher<View>(View::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("with SizeMatcher: ")
            description.appendValue(expectedWith.toString() + "x" + expectedHeight)
        }

        override fun matchesSafely(target: View): Boolean {
            val targetWidth = target.width
            val targetHeight = target.height
            return targetWidth == expectedWith && targetHeight == expectedHeight
        }

    }

    private fun listViewItemsCanBeScrolledTo(): Int {
        val listView: ListView =
            runicAlphabetActivityRule.activity.findViewById(R.id.runicAlphabetFurtherInformationListView)
        val itemHeightInDp = getFirstItemHeightOfListViewInDp(listView)
        val numberOfItems = usableSpaceInDp() / itemHeightInDp
        val numberOfItemsRounded = floor(numberOfItems)
        val canBeScrolledTo = 25 - numberOfItemsRounded
        return canBeScrolledTo.toInt()
    }

    private fun getFirstItemHeightOfListViewInDp(listView: ListView): Float {
        val heightInPx = getItemHeightofListView(listView, 1)
        return convertPixelsToDp(heightInPx.toFloat(), targetContext)
    }

    private fun getItemHeightofListView(listView: ListView, items: Int): Int {
        val adapter: ListAdapter = listView.adapter
        var grossElementHeight = 0
        val UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        for (i in 0 until items) {
            val childView: View = adapter.getView(i, null, listView)
            childView.measure(UNBOUNDED, UNBOUNDED)
            grossElementHeight += childView.measuredHeight
        }
        return grossElementHeight
    }

    private fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.getResources()
            .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    private fun actionBarHeightInDp(): Float {
        val tv = TypedValue()
        targetContext.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
        val actionBarHeight: Int = targetContext.resources.getDimensionPixelSize(tv.resourceId)
        return convertPixelsToDp(actionBarHeight.toFloat(), targetContext)
    }

    private fun androidNavBarHeightInDp(): Float {
        val resourceId =
            targetContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        val androidNavBarHeightInPx = targetContext.resources.getDimensionPixelSize(resourceId)
        return convertPixelsToDp(androidNavBarHeightInPx.toFloat(), targetContext)
    }

    private fun usableSpaceInDp(): Float {
        val screenHeightInDp = screenHeightInDp()
        val actionBarHeightInDp = actionBarHeightInDp()
        val androidNavBarHeightInDp = androidNavBarHeightInDp()
        val tabLayoutHeightInDp = actionBarHeightInDp()

        return screenHeightInDp - actionBarHeightInDp - androidNavBarHeightInDp - tabLayoutHeightInDp
    }

    private fun alphabetItemTextViewMatchesWithLetter(letter: String, viewId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("alphabetItem with $letter can be found on correct location")
            }

            override fun matchesSafely(item: View?): Boolean {
                item as LinearLayout

                val correctVerticalListView: LinearLayout =
                    item.getChildAt(letterRowIndex(letter)) as LinearLayout
                val correctAlphabetItem: View =
                    correctVerticalListView.getChildAt(letterColumnIndex(letter))
                val firstAlphabetItemLetter: TextView =
                    correctAlphabetItem.findViewById(viewId)
                val correctAlphabetItemLetterText: String = firstAlphabetItemLetter.text.toString()
                return correctAlphabetItemLetterText == letter
            }

        }
    }

    private fun letterRowIndex(letter: String): Int {
        return floor(letterIndexInAlphabet(letter).toFloat() / alphabetItemsPerRow()).toInt()
    }

    private fun letterColumnIndex(letter: String): Int {
        return letterIndexInAlphabet(letter) % alphabetItemsPerRow()
    }

    private fun letterIndexInAlphabet(letter: String): Int {
        return Alphabet.alphabetUpperCase().indexOf(letter)
    }

    private fun alphabetItemTextViewMatchesWithRune(
        letter: String,
        rune: String,
        viewId: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("alphabetItem with $rune can be found on correct location")
            }

            override fun matchesSafely(item: View?): Boolean {
                item as LinearLayout
                val letterIndexInAlphabet: Int = Alphabet.alphabetUpperCase().indexOf(letter)
                val letterRowIndex: Int =
                    floor(letterIndexInAlphabet.toFloat() / alphabetItemsPerRow()).toInt()
                val letterColumnIndex: Int = letterIndexInAlphabet % alphabetItemsPerRow()

                val correctVerticalListView: LinearLayout =
                    item.getChildAt(letterRowIndex) as LinearLayout
                val correctAlphabetItem: View =
                    correctVerticalListView.getChildAt(letterColumnIndex)
                val firstAlphabetItemLetter: TextView =
                    correctAlphabetItem.findViewById(viewId)
                val correctAlphabetItemLetterText: String = firstAlphabetItemLetter.text.toString()
                return correctAlphabetItemLetterText == rune
            }

        }
    }

    private fun alphabetItemsPerRow(): Int {
        var alphabetItemsPerRow: Int = floor(screenWidthInDp() / alphabetItemWidthInDp).toInt()
        if (alphabetItemsPerRow > 7) alphabetItemsPerRow = 7
        return alphabetItemsPerRow
    }

    private fun screenWidthInDp(): Float {
        val displayMetrics: DisplayMetrics = targetContext.resources.displayMetrics
        val screenWidthInDp: Float = displayMetrics.widthPixels / displayMetrics.density
        return screenWidthInDp
    }
}
