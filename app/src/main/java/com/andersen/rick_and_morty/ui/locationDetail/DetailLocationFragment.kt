package com.andersen.rick_and_morty.ui.locationDetail

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
import com.andersen.rick_and_morty.databinding.FragmentDetailLocationBinding
import com.andersen.rick_and_morty.domain.model.LceState
import com.andersen.rick_and_morty.domain.model.Location
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailLocationFragment : Fragment() {

    private var _binding: FragmentDetailLocationBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<DetailLocationViewModel> {
        parametersOf(args.id)
    }

    private val args by navArgs<DetailLocationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailLocationBinding.inflate(inflater, container, false)
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
                        DetailLocationFragmentDirections.actionDetailLocationFragmentToDetailCharacterFragment(
                            it.id
                        )
                    )

                }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.locationFlow.collect { lce ->
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
                viewModel.locationResidentsFlow.collect { lce ->
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

    private fun bind(location: Location) {
        with(binding) {
            name.text = location.name
            type.text = getString(R.string.type_format, location.type)
            dimension.text = getString(R.string.dimension_format, location.dimension)

            location.residents?.let {
                val idList = it.filter { url -> url.isNotBlank() }.map { url ->

                    val parts = url.split("/")
                    parts.last().toInt()

                }
                viewModel.getlocationResidents(idList)
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