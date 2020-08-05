package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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


@RunWith(AndroidJUnit4::class)
class RunicAlphabetActivityInstrumentedtests {
    lateinit var preferencesEditor: SharedPreferences.Editor
    private val ltr = LetterToRunes()
    var targetContext: Context = getInstrumentation().targetContext
    private lateinit var runes: ArrayList<Rune>
    private val alphabetUpperCase: ArrayList<String> = arrayListOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
    )

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
        runes = arrayListOf(
            ltr.getRuneInstanceOfOneChar('A')!!,
            ltr.getRuneInstanceOfOneChar('B')!!,
            ltr.getRuneInstanceOfOneChar('C')!!,
            ltr.getRuneInstanceOfOneChar('D')!!,
            ltr.getRuneInstanceOfOneChar('E')!!,
            ltr.getRuneInstanceOfOneChar('F')!!,
            ltr.getRuneInstanceOfOneChar('G')!!,
            ltr.getRuneInstanceOfOneChar('H')!!,
            ltr.getRuneInstanceOfOneChar('I')!!,
            ltr.getRuneInstanceOfOneChar('J')!!,
            ltr.getRuneInstanceOfOneChar('K')!!,
            ltr.getRuneInstanceOfOneChar('L')!!,
            ltr.getRuneInstanceOfOneChar('M')!!,
            ltr.getRuneInstanceOfOneChar('N')!!,
            ltr.getRuneInstanceOfOneChar('O')!!,
            ltr.getRuneInstanceOfOneChar('P')!!,
            ltr.getRuneInstanceOfOneChar('Q')!!,
            ltr.getRuneInstanceOfOneChar('R')!!,
            ltr.getRuneInstanceOfOneChar('S')!!,
            ltr.getRuneInstanceOfOneChar('T')!!,
            ltr.getRuneInstanceOfOneChar('U')!!,
            ltr.getRuneInstanceOfOneChar('V')!!,
            ltr.getRuneInstanceOfOneChar('W')!!,
            ltr.getRuneInstanceOfOneChar('X')!!,
            ltr.getRuneInstanceOfOneChar('Y')!!,
            ltr.getRuneInstanceOfOneChar('Z')!!
        )
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(alphabetUpperCase[x], R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(alphabetUpperCase[x], x % 20, R.id.rowLetter)
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].unicodeSymbol(),
                        x % 20,
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(alphabetUpperCase[x], R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(alphabetUpperCase[x], x % 20, R.id.rowLetter)
                )
            )
        }
    }

    @Test
    fun correctRunesInFirstColumnAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowLetter)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].unicodeSymbol(),
                        x % 20,
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowRune)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), x % 20, R.id.rowRune)
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

        for (x in 0..20) {
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
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getUpperCaseTextFromRunes(runes[x].unicodeSymbol()),
                        x % 20,
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), R.id.rowRune)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].unicodeSymbol(), x % 20, R.id.rowRune)
                )
            )
        }
    }

    @Test
    fun correctLettersInSecondColumnAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)

        for (x in 0..20) {
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
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getUpperCaseTextFromRunes(runes[x].unicodeSymbol()),
                        x % 20,
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].name(), R.id.rowRuneName)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].name(), x % 20, R.id.rowRuneName)
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

        for (x in 0..20) {
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
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].name()),
                        x % 20,
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].name(), R.id.rowRuneName)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].name(), x % 20, R.id.rowRuneName)
                )
            )
        }
    }

    @Test
    fun correctRunesStringRowRuneNameAfterSwitching() {
        moveSwitch()
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))
        Thread.sleep(500)

        for (x in 0..20) {
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
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].name()),
                        x % 20,
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

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].description(), R.id.rowDescriptionText)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].description(),
                        x % 20,
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

        for (x in 0..20) {
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
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].description()),
                        x % 20,
                        R.id.rowDescriptionText
                    )
                )
            )
        }
    }

    @Test
    fun correctLettersStringRowDescriptionTextByDefault() {
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))

        for (x in 0..20) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(runes[x].description(), R.id.rowDescriptionText)
                )
            )
            mySwipeUp(x)
        }
        //the ones on the bottom which cant be swiped
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        runes[x].description(),
                        x % 20,
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

        for (x in 0..20) {
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
        for (x in 21..25) {
            onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        ltr.getRunesFromText(runes[x].description()),
                        x % 20,
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
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "A",
                    horizontalIndex = 0,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "B",
                    horizontalIndex = 1,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "C",
                    horizontalIndex = 2,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "D",
                    horizontalIndex = 3,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "E",
                    horizontalIndex = 4,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "F",
                    horizontalIndex = 5,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "G",
                    horizontalIndex = 6,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "H",
                    horizontalIndex = 0,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "I",
                    horizontalIndex = 1,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "J",
                    horizontalIndex = 2,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "K",
                    horizontalIndex = 3,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "L",
                    horizontalIndex = 4,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "M",
                    horizontalIndex = 5,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "N",
                    horizontalIndex = 0,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "O",
                    horizontalIndex = 1,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "P",
                    horizontalIndex = 2,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "Q",
                    horizontalIndex = 3,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "R",
                    horizontalIndex = 4,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "S",
                    horizontalIndex = 5,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "T",
                    horizontalIndex = 0,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "U",
                    horizontalIndex = 1,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "V",
                    horizontalIndex = 2,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "W",
                    horizontalIndex = 3,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "X",
                    horizontalIndex = 4,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "Y",
                    horizontalIndex = 5,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "Z",
                    horizontalIndex = 6,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
    }

    @Test
    fun correctRunesAlphabetItemLetterAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        Thread.sleep(500)
        moveSwitch()
        pressButtonRunicAlphabet()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("A"),
                    horizontalIndex = 0,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("B"),
                    horizontalIndex = 1,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("C"),
                    horizontalIndex = 2,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("D"),
                    horizontalIndex = 3,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("E"),
                    horizontalIndex = 4,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("F"),
                    horizontalIndex = 5,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("G"),
                    horizontalIndex = 6,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("H"),
                    horizontalIndex = 0,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("I"),
                    horizontalIndex = 1,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("J"),
                    horizontalIndex = 2,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("K"),
                    horizontalIndex = 3,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("L"),
                    horizontalIndex = 4,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("M"),
                    horizontalIndex = 5,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("N"),
                    horizontalIndex = 0,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("O"),
                    horizontalIndex = 1,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("P"),
                    horizontalIndex = 2,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("Q"),
                    horizontalIndex = 3,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("R"),
                    horizontalIndex = 4,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("S"),
                    horizontalIndex = 5,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("T"),
                    horizontalIndex = 0,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("U"),
                    horizontalIndex = 1,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("V"),
                    horizontalIndex = 2,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("W"),
                    horizontalIndex = 3,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("X"),
                    horizontalIndex = 4,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("Y"),
                    horizontalIndex = 5,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("Z"),
                    horizontalIndex = 6,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
    }

    @Test
    fun correctLettersAlphabetItemLetterByDefault() {
        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "A",
                    horizontalIndex = 0,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "B",
                    horizontalIndex = 1,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "C",
                    horizontalIndex = 2,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "D",
                    horizontalIndex = 3,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "E",
                    horizontalIndex = 4,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "F",
                    horizontalIndex = 5,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "G",
                    horizontalIndex = 6,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "H",
                    horizontalIndex = 0,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "I",
                    horizontalIndex = 1,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "J",
                    horizontalIndex = 2,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "K",
                    horizontalIndex = 3,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "L",
                    horizontalIndex = 4,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "M",
                    horizontalIndex = 5,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "N",
                    horizontalIndex = 0,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "O",
                    horizontalIndex = 1,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "P",
                    horizontalIndex = 2,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "Q",
                    horizontalIndex = 3,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "R",
                    horizontalIndex = 4,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "S",
                    horizontalIndex = 5,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "T",
                    horizontalIndex = 0,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "U",
                    horizontalIndex = 1,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "V",
                    horizontalIndex = 2,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "W",
                    horizontalIndex = 3,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "X",
                    horizontalIndex = 4,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "Y",
                    horizontalIndex = 5,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    "Z",
                    horizontalIndex = 6,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
    }

    @Test
    fun correctRunesAlphabetItemLetterAfterSwitching() {
        moveSwitch()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("A"),
                    horizontalIndex = 0,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("B"),
                    horizontalIndex = 1,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("C"),
                    horizontalIndex = 2,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("D"),
                    horizontalIndex = 3,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("E"),
                    horizontalIndex = 4,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("F"),
                    horizontalIndex = 5,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("G"),
                    horizontalIndex = 6,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("H"),
                    horizontalIndex = 0,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("I"),
                    horizontalIndex = 1,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("J"),
                    horizontalIndex = 2,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("K"),
                    horizontalIndex = 3,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("L"),
                    horizontalIndex = 4,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("M"),
                    horizontalIndex = 5,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("N"),
                    horizontalIndex = 0,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("O"),
                    horizontalIndex = 1,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("P"),
                    horizontalIndex = 2,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("Q"),
                    horizontalIndex = 3,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("R"),
                    horizontalIndex = 4,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("S"),
                    horizontalIndex = 5,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("T"),
                    horizontalIndex = 0,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("U"),
                    horizontalIndex = 1,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("V"),
                    horizontalIndex = 2,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("W"),
                    horizontalIndex = 3,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("X"),
                    horizontalIndex = 4,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("Y"),
                    horizontalIndex = 5,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getRunesFromText("Z"),
                    horizontalIndex = 6,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemLetter
                )
            )
        )
    }

    @Test
    fun correctRuneAlphabetItemRuneByDefaultAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        pressButtonRunicAlphabet()


        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("A"),
                    horizontalIndex = 0,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("B"),
                    horizontalIndex = 1,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("C"),
                    horizontalIndex = 2,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("D"),
                    horizontalIndex = 3,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("E"),
                    horizontalIndex = 4,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("F"),
                    horizontalIndex = 5,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("G"),
                    horizontalIndex = 6,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("H"),
                    horizontalIndex = 0,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("I"),
                    horizontalIndex = 1,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("J"),
                    horizontalIndex = 2,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("K"),
                    horizontalIndex = 3,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("L"),
                    horizontalIndex = 4,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("M"),
                    horizontalIndex = 5,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("N"),
                    horizontalIndex = 0,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("O"),
                    horizontalIndex = 1,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("P"),
                    horizontalIndex = 2,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("Q"),
                    horizontalIndex = 3,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("R"),
                    horizontalIndex = 4,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("S"),
                    horizontalIndex = 5,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("T"),
                    horizontalIndex = 0,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("U"),
                    horizontalIndex = 1,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("V"),
                    horizontalIndex = 2,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("W"),
                    horizontalIndex = 3,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("X"),
                    horizontalIndex = 4,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("Y"),
                    horizontalIndex = 5,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("Z"),
                    horizontalIndex = 6,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
    }

    @Test
    fun correctLettersAlphabetItemRuneAfterSwitchingAfterIntent() {
        mainActivityRule.launchActivity(Intent())
        Thread.sleep(500)
        moveSwitch()
        pressButtonRunicAlphabet()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("A")
                    ), horizontalIndex = 0, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("B")
                    ), horizontalIndex = 1, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("C")
                    ), horizontalIndex = 2, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("D")
                    ), horizontalIndex = 3, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("E")
                    ), horizontalIndex = 4, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("F")
                    ), horizontalIndex = 5, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("G")
                    ), horizontalIndex = 6, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("H")
                    ), horizontalIndex = 0, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("I")
                    ), horizontalIndex = 1, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("J")
                    ), horizontalIndex = 2, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("K")
                    ), horizontalIndex = 3, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("L")
                    ), horizontalIndex = 4, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("M")
                    ), horizontalIndex = 5, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("N")
                    ), horizontalIndex = 0, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("O")
                    ), horizontalIndex = 1, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("P")
                    ), horizontalIndex = 2, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("Q")
                    ), horizontalIndex = 3, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("R")
                    ), horizontalIndex = 4, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("S")
                    ), horizontalIndex = 5, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("T")
                    ), horizontalIndex = 0, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("U")
                    ), horizontalIndex = 1, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("V")
                    ), horizontalIndex = 2, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("W")
                    ), horizontalIndex = 3, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("X")
                    ), horizontalIndex = 4, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("Y")
                    ), horizontalIndex = 5, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("Z")
                    ), horizontalIndex = 6, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
    }

    @Test
    fun correctRuneAlphabetItemRuneByDefault() {
        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("A"),
                    horizontalIndex = 0,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("B"),
                    horizontalIndex = 1,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("C"),
                    horizontalIndex = 2,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("D"),
                    horizontalIndex = 3,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("E"),
                    horizontalIndex = 4,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("F"),
                    horizontalIndex = 5,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("G"),
                    horizontalIndex = 6,
                    verticalIndex = 0,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("H"),
                    horizontalIndex = 0,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("I"),
                    horizontalIndex = 1,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("J"),
                    horizontalIndex = 2,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("K"),
                    horizontalIndex = 3,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("L"),
                    horizontalIndex = 4,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("M"),
                    horizontalIndex = 5,
                    verticalIndex = 1,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("N"),
                    horizontalIndex = 0,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("O"),
                    horizontalIndex = 1,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("P"),
                    horizontalIndex = 2,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("Q"),
                    horizontalIndex = 3,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("R"),
                    horizontalIndex = 4,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("S"),
                    horizontalIndex = 5,
                    verticalIndex = 2,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("T"),
                    horizontalIndex = 0,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("U"),
                    horizontalIndex = 1,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("V"),
                    horizontalIndex = 2,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("W"),
                    horizontalIndex = 3,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("X"),
                    horizontalIndex = 4,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("Y"),
                    horizontalIndex = 5,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    runeUnicodeByLetter("Z"),
                    horizontalIndex = 6,
                    verticalIndex = 3,
                    viewId = R.id.alphabetItemRune
                )
            )
        )
    }

    @Test
    fun correctLetterAlphabetItemRuneAfterSwitching() {
        moveSwitch()

        val layout: ViewInteraction = onView(withId(R.id.runicAlphabetLinearScrollLayout))
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("A")
                    ), horizontalIndex = 0, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("B")
                    ), horizontalIndex = 1, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("C")
                    ), horizontalIndex = 2, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("D")
                    ), horizontalIndex = 3, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("E")
                    ), horizontalIndex = 4, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("F")
                    ), horizontalIndex = 5, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("G")
                    ), horizontalIndex = 6, verticalIndex = 0, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("H")
                    ), horizontalIndex = 0, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("I")
                    ), horizontalIndex = 1, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("J")
                    ), horizontalIndex = 2, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("K")
                    ), horizontalIndex = 3, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("L")
                    ), horizontalIndex = 4, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("M")
                    ), horizontalIndex = 5, verticalIndex = 1, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("N")
                    ), horizontalIndex = 0, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("O")
                    ), horizontalIndex = 1, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("P")
                    ), horizontalIndex = 2, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("Q")
                    ), horizontalIndex = 3, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("R")
                    ), horizontalIndex = 4, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("S")
                    ), horizontalIndex = 5, verticalIndex = 2, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("T")
                    ), horizontalIndex = 0, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("U")
                    ), horizontalIndex = 1, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("V")
                    ), horizontalIndex = 2, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("W")
                    ), horizontalIndex = 3, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("X")
                    ), horizontalIndex = 4, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("Y")
                    ), horizontalIndex = 5, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
        layout.check(
            matches(
                alphabetItemTextViewMatchesWithText(
                    ltr.getUpperCaseTextFromRunes(
                        runeUnicodeByLetter("Z")
                    ), horizontalIndex = 6, verticalIndex = 3, viewId = R.id.alphabetItemRune
                )
            )
        )
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
        checkClickAndScroll(0, 0, 0)
        checkClickAndScroll(0, 1, 1)
        checkClickAndScroll(0, 2, 2)
        checkClickAndScroll(0, 3, 3)
        checkClickAndScroll(0, 4, 4)
        checkClickAndScroll(0, 5, 5)
        checkClickAndScroll(0, 6, 6)
        checkClickAndScroll(1, 0, 7)
        checkClickAndScroll(1, 1, 8)
        checkClickAndScroll(1, 2, 9)
        checkClickAndScroll(1, 3, 10)
        checkClickAndScroll(1, 4, 11)
        checkClickAndScroll(1, 5, 12)
        checkClickAndScroll(2, 0, 13)
        checkClickAndScroll(2, 1, 14)
        checkClickAndScroll(2, 2, 15)
        checkClickAndScroll(2, 3, 16)
        checkClickAndScroll(2, 4, 17)
        checkClickAndScroll(2, 5, 18)
        checkClickAndScroll(3, 0, 19)
        checkClickAndScroll(3, 1, 20)
        checkClickAndScroll(3, 2, 21)
        checkClickAndScroll(3, 3, 22)
        checkClickAndScroll(3, 4, 23)
        checkClickAndScroll(3, 5, 24)
        checkClickAndScroll(3, 6, 25)
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

    private fun alphabetItemTextViewMatchesWithText(
        matchingText: String,
        verticalIndex: Int,
        horizontalIndex: Int,
        viewId: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText("alphabetItem on $horizontalIndex : $verticalIndex is $matchingText")
            }

            override fun matchesSafely(item: View?): Boolean {
                val firstVerticalListView: LinearLayout =
                    (item as LinearLayout).getChildAt(verticalIndex) as LinearLayout
                val firstAlphabetItem: View = firstVerticalListView.getChildAt(horizontalIndex)
                val firstAlphabetItemLetter: TextView =
                    firstAlphabetItem.findViewById(viewId)
                val firstAlphabetItemLetterText: String = firstAlphabetItemLetter.text.toString()
                return firstAlphabetItemLetterText == matchingText
            }

        }
    }

    private fun checkClickAndScroll(
        verticalIndex: Int,
        horizontalIndex: Int,
        alphabetIndex: Int
    ): ViewInteraction {
        onView(alphabetItemAt(verticalIndex, horizontalIndex)).perform(click())
        val returnValue: ViewInteraction
        if (alphabetIndex < 21) {
            returnValue = onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        alphabetUpperCase[alphabetIndex],
                        R.id.rowLetter
                    )
                )
            )
        } else {
            returnValue = onView(withId(R.id.runicAlphabetFurtherInformationListView)).check(
                matches(
                    listViewViewContainsStringMatch(
                        alphabetUpperCase[alphabetIndex],
                        alphabetIndex % 20,
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
}
