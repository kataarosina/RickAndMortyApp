package com.andersen.rick_and_morty.ui.episodeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andersen.rick_and_morty.R
import com.andersen.rick_and_morty.adapter.EpisodeAdapter
import com.andersen.rick_and_morty.addElementDecoration
import com.andersen.rick_and_morty.addPaginationScrollListener
import com.andersen.rick_and_morty.databinding.FragmentListEpisodeBinding
import com.andersen.rick_and_morty.domain.model.Constants
import com.andersen.rick_and_morty.domain.model.EpisodeFilters
import com.andersen.rick_and_morty.domain.model.LceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ListEpisodeFragment : Fragment() {

    private var _binding: FragmentListEpisodeBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<ListEpisodeViewModel> {
        parametersOf()
    }

    val coroutineScope = CoroutineScope(Dispatchers.Main)
    var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(Constants.REQUEST_KEY_EPISODE) { _, bundle ->
            viewModel.onFilterChanged(bundle.getSerializable(Constants.EXTRA_KEY_EPISODE) as EpisodeFilters)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListEpisodeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(1000)
                    if (newText != null) {
                        viewModel.setSearchQuery(newText)
                    }
                }

                return true
            }
        })



        with(binding) {

            toolbar.setupWithNavController(findNavController())
            val adapter =
                EpisodeAdapter(requireContext()) {
                    findNavController().navigate(
                        ListEpisodeFragmentDirections.actionListEpisodeFragmentToDetailEpisodeFragment(
                            it.id
                        )
                    )

                }
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = false
            }
            val layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addElementDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(layoutManager, ITEM_TO_LOAD) {
                viewModel.onLoadEpisodes()
            }


            toolbar.menu.findItem(R.id.action_filters)
                .setOnMenuItemClickListener {
                    findNavController().navigate(
                        ListEpisodeFragmentDirections.actionListEpisodeFragmentToFilterEpisodeFragment(
                            viewModel.getFilter()
                        )
                    )
                    true
                }

            viewModel.state.onEach { lce ->
                when (lce) {
                    is LceState.Content -> {
                        isVisibleProgressBar(false)
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
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.episodesFlow.onEach {
                adapter.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }

    companion object {
        private const val SPACE_SIZE = 25
        private const val ITEM_TO_LOAD = 20
    }

}

