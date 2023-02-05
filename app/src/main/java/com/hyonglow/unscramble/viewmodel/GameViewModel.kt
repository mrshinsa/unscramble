package com.hyonglow.unscramble.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hyonglow.unscramble.ui.MAX_NO_OF_WORDS
import com.hyonglow.unscramble.ui.SCORE_INCREASE
import com.hyonglow.unscramble.ui.allWordsList

class GameViewModel : ViewModel() {
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord  : String


    val score: Int
        get() = _score
    val currentWordCount: Int
        get() = _currentWordCount
    val currentScrambledWord: String
        get() = _currentScrambledWord


    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun nextWord() : Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount =0
        wordsList.clear()
        getNextWord()
    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

}