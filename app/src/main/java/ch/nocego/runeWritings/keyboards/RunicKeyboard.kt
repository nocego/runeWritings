package ch.nocego.runeWritings.keyboards

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.InputConnection
import android.widget.*
import ch.nocego.runeWritings.R
import ch.nocego.runeWritings.letterToRuneQuiz.LetterToRuneQuizActivity


class RunicKeyboard(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    // constructors
    constructor(context: Context?) : this(context, null, 0) {}

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    private lateinit var popupWindow: PopupWindow

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

    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context?, attrs: AttributeSet?) {
        // initialize buttons
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
        mButtonDelete!!.setOnClickListener(this)
        mButtonEnter!!.setOnClickListener(this)

        val popupView: View =
            LayoutInflater.from(context).inflate(R.layout.key_popup_menu, FrameLayout(context!!))
        popupWindow = PopupWindow(context)
        popupWindow.contentView = popupView

        val touchListener = OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    showKeyPopup(v)
                }
                MotionEvent.ACTION_UP -> {
                    dismissKeyPopupAndCommitText(v)
                }
            }
            true
        }

        //set button touch listeners for runes
        mButtonFehu!!.setOnTouchListener(touchListener)
        mButtonUr!!.setOnTouchListener(touchListener)
        mButtonAnsuz!!.setOnTouchListener(touchListener)
        mButtonRaido!!.setOnTouchListener(touchListener)
        mButtonKaun!!.setOnTouchListener(touchListener)
        mButtonGyfu!!.setOnTouchListener(touchListener)
        mButtonWynn!!.setOnTouchListener(touchListener)
        mButtonHaglaz!!.setOnTouchListener(touchListener)
        mButtonNaudiz!!.setOnTouchListener(touchListener)
        mButtonIsaz!!.setOnTouchListener(touchListener)
        mButtonJeran!!.setOnTouchListener(touchListener)
        mButtonEihwaz!!.setOnTouchListener(touchListener)
        mButtonPeord!!.setOnTouchListener(touchListener)
        mButtonAlgiz!!.setOnTouchListener(touchListener)
        mButtonSigel!!.setOnTouchListener(touchListener)
        mButtonTiwaz!!.setOnTouchListener(touchListener)
        mButtonBerkanan!!.setOnTouchListener(touchListener)
        mButtonEhwaz!!.setOnTouchListener(touchListener)
        mButtonMannaz!!.setOnTouchListener(touchListener)
        mButtonLaguz!!.setOnTouchListener(touchListener)
        mButtonOdal!!.setOnTouchListener(touchListener)
        mButtonDagaz!!.setOnTouchListener(touchListener)
    }

    override fun onClick(v: View) {
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

    private fun showKeyPopup(v: View) {
        //The view pressed is a button.
        val button = v as Button

        popupWindow.width = v.width + 20
        popupWindow.height = v.height + 20
        val keyboardKey: TextView = popupWindow.contentView.findViewById(R.id.keyboard_key)
        keyboardKey.text = button.text.toString()

        //get button location to show the popup above it.
        val keyLocation = IntArray(2)
        button.getLocationOnScreen(keyLocation)
        val location = Rect()
        location.left = keyLocation[0]
        location.top = keyLocation[1]
        location.right = location.left + button.width
        location.bottom = location.top + button.height

        popupWindow.showAtLocation(
            v,
            Gravity.NO_GRAVITY,
            location.left - 10,
            location.top - button.height - 20
        )
    }

    private fun dismissKeyPopupAndCommitText(v: View) {
        //The view pressed is a button.
        val button = v as Button

        popupWindow.dismiss()
        val value = button.text.toString()
        inputConnection!!.commitText(value, 1)
    }
}