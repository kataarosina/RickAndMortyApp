package com.andersen.rick_and_morty.ui.characterDetail

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
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.andersen.rick_and_morty.R
import com.andersen.rick_and_morty.adapter.EpisodeAdapter
import com.andersen.rick_and_morty.databinding.FragmentDetailCharacterBinding
import com.andersen.rick_and_morty.domain.model.Character
import com.andersen.rick_and_morty.domain.model.LceState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailCharacterFragment : Fragment() {

    private var _binding: FragmentDetailCharacterBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<DetailCharacterViewModel> {
        parametersOf(args.id)
    }

    private val args by navArgs<DetailCharacterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailCharacterBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.setupWithNavController(findNavController())

            val adapter =
                EpisodeAdapter(requireContext()) {
                    findNavController().navigate(
                        DetailCharacterFragmentDirections.actionDetailCharacterFragmentToDetailEpisodeFragment2(
                            it.id
                        )
                    )
                }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.characterFlow.collect { lce ->
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
                viewModel.locationFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                            binding.location.text =  getString(R.string.location_format, lce.data.name)

                            location.setOnClickListener {
                                findNavController().navigate(
                                    DetailCharacterFragmentDirections.actionDetailCharacterFragmentToDetailLocationFragment(
                                        lce.data.id
                                    )
                                )
                            }
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
                viewModel.originFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                            binding.origin.text = getString(R.string.origin_format, lce.data.name)
                            origin.setOnClickListener {
                                findNavController().navigate(
                                    DetailCharacterFragmentDirections.actionDetailCharacterFragmentToDetailLocationFragment(
                                        lce.data.id
                                    )
                                )
                            }
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
                viewModel.characterEpisodesFlow.collect { lce ->
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

    private fun bind(character: Character) {
        with(binding) {
            imageView.load(character.image)
            name.text = character.name
            species.text = getString(R.string.species_format, character.species)
            status.text = getString(R.string.status_format, character.status)
            gender.text = getString(R.string.gender_format, character.gender)

            character.episode?.let {
                val idList = it.map { url ->
                    val parts = url.split("/")
                    parts.last().toInt()
                }
                viewModel.getCharacterEpisodes(idList)
            }
            character.location?.let { location ->
                if (location.isNotBlank()) {
                    val idLocation = location.split("/").last().toInt()
                    viewModel.getLocation(idLocation)
                }
            }

            character.origin?.let { origin ->
                if (origin.isNotBlank()) {
                    val idOrigin = origin.split("/").last().toInt()
                    viewModel.getOrigin(idOrigin)
                }
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