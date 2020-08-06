package ch.nocego.runeWritings.keyboards

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.letterToRuneQuiz.LetterToRuneQuizActivity

class RunicKeyboard(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    // constructors
    constructor(context: Context?) : this(context, null, 0) {}

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    // keyboard keys (buttons)
    private var mButtonFehu: Button? = null
    private var mButtonUr: Button? = null
    private var mButtonAnsuz: Button? = null
    private var mButtonRaido: Button? = null
    private var mButtonKaun: Button? = null
    private var mButtonGyfu: Button? = null
    private var mButtonWynn: Button? = null
    private var mButtonHaglaz: Button? = null
    private var mButtonNaudiz: Button? = null
    private var mButtonIsaz: Button? = null
    private var mButtonJeran: Button? = null
    private var mButtonEihwaz: Button? = null
    private var mButtonPeord: Button? = null
    private var mButtonAlgiz: Button? = null
    private var mButtonSigel: Button? = null
    private var mButtonTiwaz: Button? = null
    private var mButtonBerkanan: Button? = null
    private var mButtonEhwaz: Button? = null
    private var mButtonMannaz: Button? = null
    private var mButtonLaguz: Button? = null
    private var mButtonOdal: Button? = null
    private var mButtonDagaz: Button? = null
    private var mButtonDelete: Button? = null
    private var mButtonEnter: Button? = null

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    var keyValues = SparseArray<String>()

    // Our communication link to the EditText
    private var inputConnection: InputConnection? = null

    private var letterToRuneQuizActivity: LetterToRuneQuizActivity? = null

    private fun init(context: Context?, attrs: AttributeSet?) { // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.runic_keyboard, this, true)
        mButtonFehu = findViewById<View>(R.id.button_fehu) as Button
        mButtonUr = findViewById<View>(R.id.button_ur) as Button
        mButtonAnsuz = findViewById<View>(R.id.button_ansuz) as Button
        mButtonRaido = findViewById<View>(R.id.button_raido) as Button
        mButtonKaun = findViewById<View>(R.id.button_kaun) as Button
        mButtonGyfu = findViewById<View>(R.id.button_gyfu) as Button
        mButtonWynn = findViewById<View>(R.id.button_wynn) as Button
        mButtonHaglaz = findViewById<View>(R.id.button_haglaz) as Button
        mButtonNaudiz = findViewById<View>(R.id.button_naudiz) as Button
        mButtonIsaz = findViewById<View>(R.id.button_isaz) as Button
        mButtonJeran = findViewById<View>(R.id.button_jeran) as Button
        mButtonEihwaz = findViewById<View>(R.id.button_eihwaz) as Button
        mButtonPeord = findViewById<View>(R.id.button_peord) as Button
        mButtonAlgiz = findViewById<View>(R.id.button_algiz) as Button
        mButtonSigel = findViewById<View>(R.id.button_sigel) as Button
        mButtonTiwaz = findViewById<View>(R.id.button_tiwaz) as Button
        mButtonBerkanan = findViewById<View>(R.id.button_berkanan) as Button
        mButtonEhwaz = findViewById<View>(R.id.button_ehwaz) as Button
        mButtonMannaz = findViewById<View>(R.id.button_mannaz) as Button
        mButtonLaguz = findViewById<View>(R.id.button_laguz) as Button
        mButtonOdal = findViewById<View>(R.id.button_odal) as Button
        mButtonDagaz = findViewById<View>(R.id.button_dagaz) as Button
        mButtonDelete = findViewById<View>(R.id.button_delete) as Button
        mButtonEnter = findViewById<View>(R.id.button_enter) as Button

        // set button click listeners
        mButtonFehu!!.setOnClickListener(this)
        mButtonUr!!.setOnClickListener(this)
        mButtonAnsuz!!.setOnClickListener(this)
        mButtonRaido!!.setOnClickListener(this)
        mButtonKaun!!.setOnClickListener(this)
        mButtonGyfu!!.setOnClickListener(this)
        mButtonWynn!!.setOnClickListener(this)
        mButtonHaglaz!!.setOnClickListener(this)
        mButtonNaudiz!!.setOnClickListener(this)
        mButtonIsaz!!.setOnClickListener(this)
        mButtonJeran!!.setOnClickListener(this)
        mButtonEihwaz!!.setOnClickListener(this)
        mButtonPeord!!.setOnClickListener(this)
        mButtonAlgiz!!.setOnClickListener(this)
        mButtonSigel!!.setOnClickListener(this)
        mButtonTiwaz!!.setOnClickListener(this)
        mButtonBerkanan!!.setOnClickListener(this)
        mButtonEhwaz!!.setOnClickListener(this)
        mButtonMannaz!!.setOnClickListener(this)
        mButtonLaguz!!.setOnClickListener(this)
        mButtonOdal!!.setOnClickListener(this)
        mButtonDagaz!!.setOnClickListener(this)
        mButtonDelete!!.setOnClickListener(this)
        mButtonEnter!!.setOnClickListener(this)

        // map buttons IDs to input strings
        keyValues.put(R.id.button_fehu, "\u16A0")
        keyValues.put(R.id.button_ur, "\u16A2")
        keyValues.put(R.id.button_ansuz, "\u16A8")
        keyValues.put(R.id.button_raido, "\u16B1")
        keyValues.put(R.id.button_kaun, "\u16B4")
        keyValues.put(R.id.button_gyfu, "\u16B7")
        keyValues.put(R.id.button_wynn, "\u16B9")
        keyValues.put(R.id.button_haglaz, "\u16BA")
        keyValues.put(R.id.button_naudiz, "\u16BE")
        keyValues.put(R.id.button_isaz, "\u16C1")
        keyValues.put(R.id.button_jeran, "\u16C3")
        keyValues.put(R.id.button_eihwaz, "\u16C7")
        keyValues.put(R.id.button_peord, "\u16C8")
        keyValues.put(R.id.button_algiz, "\u16C9")
        keyValues.put(R.id.button_sigel, "\u16CB")
        keyValues.put(R.id.button_tiwaz, "\u16CF")
        keyValues.put(R.id.button_berkanan, "\u16D2")
        keyValues.put(R.id.button_ehwaz, "\u16D6")
        keyValues.put(R.id.button_mannaz, "\u16D7")
        keyValues.put(R.id.button_laguz, "\u16DA")
        keyValues.put(R.id.button_odal, "\u16DF")
        keyValues.put(R.id.button_dagaz, "\u16DE")
    }

    override fun onClick(v: View) { // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return
        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.id == R.id.button_delete) {
            val selectedText = inputConnection!!.getSelectedText(0)
            if (TextUtils.isEmpty(selectedText)) { // no selection, so delete previous character
                inputConnection!!.deleteSurroundingText(1, 0)
            } else { // delete the selection
                inputConnection!!.commitText("", 1)
            }
        } else if (v.id == R.id.button_enter) {
            letterToRuneQuizActivity!!.checkRune()
        } else {
            val value = keyValues[v.id]
            inputConnection!!.commitText(value, 1)
        }
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    fun setInputConnection(ic: InputConnection?) {
        inputConnection = ic
    }

    fun setLetterToRunesQuizActivity(letterToRuneQuizActivity: LetterToRuneQuizActivity) {
        this.letterToRuneQuizActivity = letterToRuneQuizActivity
    }

    init {
        init(context, attrs)
    }
}