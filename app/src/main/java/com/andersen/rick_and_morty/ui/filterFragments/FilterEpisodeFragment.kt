package com.andersen.rick_and_morty.ui.filterFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.andersen.rick_and_morty.databinding.FragmentFilterEpisodeBinding
import com.andersen.rick_and_morty.domain.model.Constants
import com.andersen.rick_and_morty.domain.model.EpisodeFilters
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterEpisodeFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterEpisodeBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<FilterEpisodeFragmentArgs>()

    private var episode: String = ""
    private var air_date: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentFilterEpisodeBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            episodeEditText.setText(args.filter.episode)
            airDateEditText.setText(args.filter.airDate)

            episodeEditText.addTextChangedListener { text ->
                episode = text.toString()
            }
            airDateEditText.addTextChangedListener { text ->
                air_date = text.toString()
            }

            buttonCancel.setOnClickListener {
                onCancelClicked()
            }
            buttonOk.setOnClickListener {
                onSearchClicked()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    private fun onSearchClicked() {
        val filters = EpisodeFilters(episode, air_date, "")
        setFragmentResult(
            Constants.REQUEST_KEY_EPISODE,
            bundleOf(Constants.EXTRA_KEY_EPISODE to filters)
        )
        dismiss()
    }

    private fun onCancelClicked() {
        dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}