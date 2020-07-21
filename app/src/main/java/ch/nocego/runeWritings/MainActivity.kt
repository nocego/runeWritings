package ch.nocego.runeWritings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun switchRunesToggle(v: View) {
        changeTexts()
    }

    fun generateUnicodeRunesIntent(v: View) {
        val intent = Intent(this, UnicodeActivity::class.java)
        startActivity(intent)
    }

    private fun changeTexts() {
        changeTitle()
    }

    private fun changeTitle() {
        if(switchRunes.isChecked){
            mainActivityTitle.text = getString(R.string.titleRunic)
        }else{
            mainActivityTitle.text = getString(R.string.title)
        }
    }
}
