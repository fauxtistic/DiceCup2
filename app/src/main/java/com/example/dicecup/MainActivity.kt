package com.example.dicecup

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val HISTORY_NAME = "history"
    private val TAG = "mytagmain"
    private val DICE_NUMBER_NAME = "dicenumber"
    private val REQUEST_CODE_HISTORY = 10

    val mGenerator = Random()
    val diceIds = arrayOf(0, R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
            R.drawable.dice4, R.drawable.dice5, R.drawable.dice6)
    val mHistory = mutableListOf<DiceThrow>()
    var numberOfDice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val diceRange = arrayOf(1, 2, 3, 4, 5, 6)
        val spAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, diceRange)
        spDice.adapter = spAdapter
        spDice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                numberOfDice = diceRange[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*val orientation = this.getResources().getConfiguration().orientation
        val message = if (orientation == Configuration.ORIENTATION_PORTRAIT) "Portrait" else "Landscape"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()*/
        numberOfDice = 1

        if (savedInstanceState != null) {
            val history = savedInstanceState.getSerializable(HISTORY_NAME) as Array<DiceThrow> //casting
            history.forEach { d -> mHistory.add(d) }
            numberOfDice = savedInstanceState.getInt(DICE_NUMBER_NAME)
        }
        val position = diceRange.indexOf(numberOfDice)
        spDice.setSelection(position)
        updateDicePictures()
    }

    fun onClickRoll(view: View) {

        // for testing - should be selectable by user
        // must lie at top of function for reasons of turnsafety
        // numberOfDice = mGenerator.nextInt(6) + 1

        val throws = Array(numberOfDice) { i -> mGenerator.nextInt(6) + 1 }
        mHistory.add(DiceThrow(Date(), throws))
        updateDicePictures()
    }

    private fun updateDicePictures() {
        llDiceBoard.removeAllViewsInLayout()
        if (mHistory.size > 0) {
            for (i in 0 until numberOfDice) {
                val d = ImageView(this)
                llDiceBoard.addView(d)
                d.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                d.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                d.maxWidth = 250
                d.scaleType = ImageView.ScaleType.CENTER
                d.adjustViewBounds = true
                d.setImageResource(diceIds[(mHistory.last().dies[i])])
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(HISTORY_NAME, mHistory.toTypedArray())
        outState.putInt(DICE_NUMBER_NAME, numberOfDice)
    }

    fun onClickGotoHistory(view: View) {
        val intent = Intent(this, HistoryActivity::class.java)
        intent.putExtra("data", mHistory.toTypedArray())
        // should numberofdice be remembered??
        startActivityForResult(intent, REQUEST_CODE_HISTORY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_HISTORY) {
            if (resultCode == 1) {
                mHistory.clear()
                updateDicePictures()
                Toast.makeText(this, "History cleared", Toast.LENGTH_LONG).show()
            }
        }
    }


}