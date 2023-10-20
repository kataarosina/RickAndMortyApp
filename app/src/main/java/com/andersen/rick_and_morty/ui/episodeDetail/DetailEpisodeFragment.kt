package com.andersen.rick_and_morty.ui.episodeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andersen.rick_and_morty.R
import com.andersen.rick_and_morty.adapter.CharacterAdapter
import com.andersen.rick_and_morty.databinding.FragmentDetailEpisodeBinding
import com.andersen.rick_and_morty.domain.model.Episode
import com.andersen.rick_and_morty.domain.model.LceState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailEpisodeFragment : Fragment() {

    private var _binding: FragmentDetailEpisodeBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<DetailEpisodeViewModel> {
        parametersOf(args.id)
    }

    private val args by navArgs<DetailEpisodeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailEpisodeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.setupWithNavController(findNavController())
            val adapter =
                CharacterAdapter(requireContext()) {
                    findNavController().navigate(
                        DetailEpisodeFragmentDirections.actionDetailEpisodeFragmentToDetailCharacterFragment(
                            it.id
                        )
                    )
                }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.episodeFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                            bind(lce.data)
                        }

                        is LceState.Error -> {
                            isVisibleProgressBar(false)
                            Toast.makeText(
                                requireContext(),
                                lce.throwable.message ?: "", Toast.LENGTH_SHORT
                            ).show()
                        }

                        LceState.Loading -> {
                            isVisibleProgressBar(true)
                        }
                    }


                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.episodeCharactersFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                            adapter.submitList(lce.data)
                        }

                        is LceState.Error -> {
                            isVisibleProgressBar(false)
                            Toast.makeText(
                                requireContext(),
                                lce.throwable.message ?: "", Toast.LENGTH_SHORT
                            ).show()
                        }

                        LceState.Loading -> {
                            isVisibleProgressBar(true)
                        }
                    }

                }
            }
        }
    }

    private fun bind(episode: Episode) {
        with(binding) {

            name.text = episode.name
            numberEpisode.text = getString(R.string.episode_format, episode.episode)
            airDate.text = getString(R.string.air_date_format, episode.airDate)
            airDate.text = episode.airDate
            episode.characters?.let {
                val idList = it.map { url ->
                    val parts = url.split("/")
                    parts.last().toInt()
                }
                viewModel.getEpisodeCharacters(idList)
            }


        }
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }

    companion object;

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}