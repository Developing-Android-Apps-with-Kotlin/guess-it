package se.stylianosgakis.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_PANIC_SECONDS = 5L
        private const val COUNTDOWN_TIME = 8000L
    }

    private val _currentWord = MutableLiveData("")
    val currentWord: LiveData<String> = _currentWord
    private val _currentScore = MutableLiveData(0)
    val currentScore: LiveData<Int> = _currentScore
    private val _eventGameFinish = MutableLiveData(false)
    val eventGameFinish: LiveData<Boolean> = _eventGameFinish
    private val _eventBuzz: MutableLiveData<BuzzType> = MutableLiveData(BuzzType.NoBuzz)
    val eventBuzz: LiveData<BuzzType> = _eventBuzz
    private val _currentTime = MutableLiveData(COUNTDOWN_TIME)
    val currentTime: LiveData<Long> = _currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    private lateinit var wordList: MutableList<String>
    private val countDownTimer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        override fun onFinish() {
            _currentTime.value = DONE
            _eventBuzz.value = BuzzType.GameOver
            _eventGameFinish.value = true
        }

        override fun onTick(millisUntilFinished: Long) {
            val secondsToFinish = (millisUntilFinished / ONE_SECOND)
            _currentTime.value = secondsToFinish
            if (secondsToFinish < COUNTDOWN_PANIC_SECONDS) {
                _eventBuzz.value = BuzzType.CountdownPanic
            }
        }
    }.start()

    init {
        resetList()
        nextWord()
    }

    private fun resetList() {
        wordList = mutableListOf("first", "second")
        wordList.shuffle()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }
        _currentWord.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _currentScore.value = _currentScore.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _currentScore.value = _currentScore.value?.plus(1)
        _eventBuzz.value = BuzzType.Correct
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NoBuzz
    }
}