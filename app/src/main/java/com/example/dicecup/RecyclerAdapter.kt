package com.example.dicecup

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.DiceHolder> {

    private val mDataset: Array<DiceThrow>
    private val mInflater: LayoutInflater
    private val mItemListener: IClickItemListener
    private val mContext: Context
    val diceIds = arrayOf(0, R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
            R.drawable.dice4, R.drawable.dice5, R.drawable.dice6)


    constructor(context: Context, diceThrows: Array<DiceThrow>, itemClickListener: IClickItemListener) : super() {
        mInflater = LayoutInflater.from(context)
        mDataset = diceThrows
        mItemListener = itemClickListener
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiceHolder {
        val view: View = mInflater.inflate(R.layout.cell_history, parent, false)
        return DiceHolder(view)
    }

    override fun onBindViewHolder(holder: DiceHolder, position: Int) {
        val d = mDataset[position]
        holder.mTime.text = d.getTimeString() + ":"
        holder.mDice.removeAllViewsInLayout()
        for (element in d.dies) {
            val d = ImageView(mContext)
            holder.mDice.addView(d)
            d.maxWidth = 110
            d.adjustViewBounds = true
            d.setImageResource(diceIds[element])
        }

        holder.view.setOnClickListener { v -> mItemListener.onDiceThrowsClick(d, position) }
        holder.view.setBackgroundColor(if (position % 2 == 0) Color.parseColor("#BBBBBB") else Color.parseColor("#CCCCCC"))
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    class DiceHolder : RecyclerView.ViewHolder {

        val view: View
        val mTime: TextView
        val mDice: LinearLayout

        constructor(v: View) : super(v) {
            view = v
            mTime = v.findViewById(R.id.tvTime)
            mDice = v.findViewById(R.id.llDiceThrows)
        }
    }
}