package se.stylianosgakis.guessit.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(
    finalScore: Int
) : ViewModel() {
    private val _finalScore = MutableLiveData(finalScore.toString())
    val finalScore: LiveData<String> = _finalScore
    private val _eventPlayAgain = MutableLiveData(false)
    val eventPlayAgain: LiveData<Boolean> = _eventPlayAgain

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}