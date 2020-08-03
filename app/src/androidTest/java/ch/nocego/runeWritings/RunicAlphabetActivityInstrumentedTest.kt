package ch.nocego.runeWritings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import ch.nocego.runeWritings.contextHolder.ContextHolder
import ch.nocego.runeWritings.runes.LetterToRunes
import ch.nocego.runeWritings.runes.Rune
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
}
