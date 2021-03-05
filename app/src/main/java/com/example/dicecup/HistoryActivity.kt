package com.example.dicecup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity(), IClickItemListener {
    private val TAG = "mytaghistory"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        if (intent.extras != null) {
            val extras: Bundle = intent.extras!!
            val data = extras.getSerializable("data") as Array<DiceThrow>

            val mReView = findViewById<RecyclerView>(R.id.recyclerView)
            mReView.layoutManager = LinearLayoutManager(this)
            mReView.adapter = RecyclerAdapter(this, data, this)
        } else {
            Log.d(TAG, "intent.extras is null")
        }

    }

    override fun onDiceThrowsClick(diceThrow: DiceThrow, position: Int) {
        Toast.makeText(this, "Position $position clicked", Toast.LENGTH_LONG).show()
        // Toast.makeText(this, "Position $position clicked: $diceThrow", Toast.LENGTH_LONG).show()
    }

    fun onClickGoBack(view: View) {
        setResult(0)
        finish()
    }

    fun onClickClearHistory(view: View) {
        setResult(1)
        finish()
    }

}