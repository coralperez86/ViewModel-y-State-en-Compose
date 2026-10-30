package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var currentWord = ""
    private var score = 0
    private var wordCount = 0

    init {
        startGame()
    }

    fun startGame() {
        score = 0
        wordCount = 0
        nextWord()
    }

    private fun nextWord() {
        if (wordCount >= MAX_NO_OF_WORDS) {
            _uiState.update { it.copy(showFinalScore = true) }
            return
        }
        currentWord = allWords.random()
        val scrambled = scrambleWord(currentWord)
        wordCount++
        _uiState.update {
            it.copy(
                currentScrambledWord = scrambled,
                userWord = "",
                score = score,
                wordCount = wordCount,
                showFinalScore = false
            )
        }
    }

    fun scrambleWord(word: String): String {
        val shuffled = word.toList().shuffled().joinToString("")
        return if (shuffled == word) scrambleWord(word) else shuffled
    }

    fun submit(word: String) {
        if (word.equals(currentWord, ignoreCase = true)) {
            score += SCORE_INCREASE
        }
        nextWord()
    }

    fun skip() {
        nextWord()
    }

    fun updateUserWord(word: String) {
        _uiState.update { it.copy(userWord = word) }
    }
}
