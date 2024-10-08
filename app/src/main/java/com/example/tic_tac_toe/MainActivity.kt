package com.example.tic_tac_toe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isPlayerXTurn = true
    private val board = Array(3) { IntArray(3) }  // 0 for empty, 1 for X, 2 for O
    private lateinit var statusTextView: TextView
    private lateinit var buttons: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)

        // Initialize button grid
        buttons = arrayOf(
            findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
            findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
        )

        // Set click listeners for all buttons
        buttons.forEachIndexed { index, button ->
            val row = index / 3
            val col = index % 3
            button.setOnClickListener { onButtonClick(row, col, button) }
        }

        // Reset button listener
        findViewById<Button>(R.id.resetButton).setOnClickListener { resetGame() }
    }

    private fun onButtonClick(row: Int, col: Int, button: Button) {
        if (board[row][col] != 0) return  // Cell already filled

        // Update board state
        board[row][col] = if (isPlayerXTurn) 1 else 2
        button.text = if (isPlayerXTurn) "X" else "O"

        if (checkWinner()) {
            statusTextView.text = if (isPlayerXTurn) getString(R.string.player_x_wins) else getString(R.string.player_o_wins)
            disableAllButtons()
        } else if (isBoardFull()) {
            statusTextView.text = getString(R.string.draw)
        } else {
            isPlayerXTurn = !isPlayerXTurn
            statusTextView.text = if (isPlayerXTurn) getString(R.string.player_x_turn) else getString(R.string.player_o_turn)
        }
    }

    private fun checkWinner(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) return true
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) return true
        }
        return (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) ||
                (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0)
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { cell -> cell != 0 } }
    }

    private fun disableAllButtons() {
        buttons.forEach { it.isEnabled = false }
    }

    private fun resetGame() {
        // Reset game state
        isPlayerXTurn = true
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = 0
            }
        }
        statusTextView.text = getString(R.string.player_x_turn)

        // Reset buttons
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }
    }
}
