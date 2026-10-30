package com.example.unscramble.ui

data class GameUiState(
    val currentScrambledWord: String = "",
    val userWord: String = "",
    val score: Int = 0,
    val wordCount: Int = 0,
    val showFinalScore: Boolean = false
)
