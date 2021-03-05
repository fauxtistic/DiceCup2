package com.example.dicecup

interface IClickItemListener {

    fun onDiceThrowsClick(diceThrow: DiceThrow, position: Int)
}