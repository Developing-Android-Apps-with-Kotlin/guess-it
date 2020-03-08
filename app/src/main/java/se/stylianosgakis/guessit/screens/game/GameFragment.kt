package se.stylianosgakis.guessit.screens.game

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import se.stylianosgakis.guessit.R
import se.stylianosgakis.guessit.databinding.GameFragmentBinding

class GameFragment : Fragment() {
    private lateinit var binding: GameFragmentBinding
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false
        )
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                val action = GameFragmentDirections.actionGameToScore(
                    score = viewModel.currentScore.value ?: 0
                )
                findNavController(this).navigate(action)
                viewModel.onGameFinishComplete()
            }
        })
        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != BuzzType.NoBuzz) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
        return binding.root
    }

    private fun buzz(pattern: LongArray) {
        activity?.getSystemService<Vibrator>()?.vibrate(
            VibrationEffect.createWaveform(pattern, -1)
        )
    }
}
