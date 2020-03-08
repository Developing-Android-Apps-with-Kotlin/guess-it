package se.stylianosgakis.guessit.screens.game

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 100)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 500)
private val NO_BUZZ_PATTERN = longArrayOf(0)

sealed class BuzzType(val pattern: LongArray) {
    object Correct : BuzzType(CORRECT_BUZZ_PATTERN)
    object CountdownPanic : BuzzType(PANIC_BUZZ_PATTERN)
    object GameOver : BuzzType(GAME_OVER_BUZZ_PATTERN)
    object NoBuzz : BuzzType(NO_BUZZ_PATTERN)
}