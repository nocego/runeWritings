package ch.nocego.runeWritings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ch.nocego.runeWritings.runes.LetterToUnicode
import kotlinx.android.synthetic.main.activity_unicode.*
import java.lang.StringBuilder

class UnicodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unicode)

        val actionbar = supportActionBar
        actionbar!!.title = getText(R.string.lettersToUnicodeRunes)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun generateUnicode(v: View) {
        val letterString: String = textInLetters.text.toString().replace("\\n", "<br />")
        val ltu = LetterToUnicode()
        val stringBuilder = StringBuilder()

        for(i in letterString.indices){
            val unicodeLetter = ltu.getEscapedUnicodeOfOneChar(letterString[i])
            stringBuilder.append(unicodeLetter)
        }
        Log.v("generated Unicode", stringBuilder.toString())
    }
}
