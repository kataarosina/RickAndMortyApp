package com.andersen.rick_and_morty.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andersen.rick_and_morty.R
import com.andersen.rick_and_morty.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {

    private var _binding: ActivitySplashBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ActivitySplashBinding.inflate(inflater, container, false).also { binding ->
                _binding = binding
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imgBackgroud.animate().translationY(-2500.0f).setDuration(1000).startDelay = 2000
            animationView.animate().translationY(1500.0f).setDuration(1000).startDelay = 2000
        }
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_splashScreen_to_contentFragment)
        }
    }
}