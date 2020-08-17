package ch.nocego.runeWritings.letterToRuneQuiz

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.model.Alphabet
import ch.nocego.runeWritings.model.LetterToRunes
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileActionBar
import ch.nocego.runeWritings.model.Transpiler.Companion.transpileTextResourceOnTextView
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs
import ch.nocego.runeWritings.sharedPrefs.SharedPrefs.Companion.getUseRunes
import kotlinx.android.synthetic.main.activity_letter_to_rune_quiz.*
import java.util.*

class LetterToRuneQuizActivity : AppCompatActivity() {

    var actionbar: ActionBar? = null
    var useRunesSwitch: Switch? = null
    var currentLetter: String? = null
    val ltr: LetterToRunes = LetterToRunes()
    var correctCounter: Int = 0
    var alphabeticLetterIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_to_rune_quiz)

        val b: Bundle? = intent.extras
        b?.let {
            alphabeticLetterIndex = it.getInt("letterIndex")
        }

        randomLetter()
        letterToRuneQuizLetter.text = currentLetter
        correctRunesInARowCounter.text = correctCounter.toString()
        bestSoFar.text = SharedPrefs.getBestLetterToRuneQuiz().toString()

        actionbar = supportActionBar
        actionbar!!.title = getString(R.string.letterToRuneQuiz).toUpperCase(Locale.ROOT)
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        letterToRuneQuizRune.setRawInputType(InputType.TYPE_CLASS_TEXT)
        letterToRuneQuizRune.setTextIsSelectable(false)

        val ic: InputConnection = letterToRuneQuizRune.onCreateInputConnection(EditorInfo())
        runicKeyboard.setLetterToRunesQuizActivity(this)
        runicKeyboard.setInputConnection(ic)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.useRunesMenuItem)
        item.setActionView(R.layout.switch_layout)

        val mySwitch = item.actionView.findViewById<Switch>(R.id.useRunesSwitch)
        useRunesSwitch = mySwitch
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = SharedPrefs.getSharedPrefs().edit()
            editor.putBoolean("useRunes", isChecked)
            editor.apply()
            transpileTexts()
        }

        useRunesSwitch!!.isChecked = getUseRunes()
        transpileTexts()

        return true
    }

    override fun onPause() {
        super.onPause()
        saveIfNewBest()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveIfNewBest()
    }

    fun checkRune() {
        val runeRandom: String = ltr.getRunesFromText(currentLetter!!)
        val runeEntered: String = letterToRuneQuizRune.text.toString()
        if (runeRandom == runeEntered) {
            letterToRuneQuizRune.setTextColor(Color.parseColor("#000000"))
            letterToRuneQuizRune.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#000000"))
            letterToRuneQuizRune.setText("")
            incrementCorrectCounter()
            newLetter()
        } else {
            letterToRuneQuizRune.setTextColor(Color.parseColor("#ff0000"))
            letterToRuneQuizRune.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#ff0000"))
            resetCorrectCounter()
        }
    }

    private fun transpileTexts() {
        transpileActionBar(R.string.letterToRuneQuiz, actionbar!!)
        transpileTextResourceOnTextView(useRunesSwitch!!, R.string.useRunes)
        transpileTextResourceOnTextView(correctRunesInARow, R.string.correctRunesInARow)
        transpileTextResourceOnTextView(best, R.string.best)
        transpileTextResourceOnTextView(
            runicKeyboard.findViewById<Button>(R.id.button_delete),
            R.string.delete
        )
        transpileTextResourceOnTextView(
            runicKeyboard.findViewById<Button>(R.id.button_enter),
            R.string.enter
        )
    }

    private fun randomLetter() {
        val randomNumber: Int
        if (alphabeticLetterIndex != null && alphabeticLetterIndex!! < 26) {
            randomNumber = alphabeticLetterIndex!!
            alphabeticLetterIndex = alphabeticLetterIndex!! + 1
        } else {
            randomNumber = (0..25).random()
        }
        currentLetter = Alphabet.alphabetUpperCase()[randomNumber]
    }

    private fun resetCorrectCounter() {
        saveIfNewBest()
        correctCounter = 0
        updateCorrectRunesInARowView()
    }

    private fun saveIfNewBest() {
        if (correctCounter > SharedPrefs.getBestLetterToRuneQuiz()) {
            SharedPrefs.setBestLetterToRuneQuiz(correctCounter)
            bestSoFar.text = correctCounter.toString()
        }
    }

    private fun incrementCorrectCounter() {
        correctCounter += 1
        updateCorrectRunesInARowView()
    }

    private fun updateCorrectRunesInARowView() {
        correctRunesInARowCounter.text = correctCounter.toString()
    }

    private fun newLetter() {
        randomLetter()
        letterToRuneQuizLetter.text = currentLetter
    }
}
