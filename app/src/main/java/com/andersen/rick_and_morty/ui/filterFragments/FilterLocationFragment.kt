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
import com.andersen.rick_and_morty.databinding.FragmentFilterLocationBinding
import com.andersen.rick_and_morty.domain.model.Constants
import com.andersen.rick_and_morty.domain.model.LocationFilters
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterLocationFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterLocationBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<FilterLocationFragmentArgs>()

    private var type: String = ""
    private var dimension: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentFilterLocationBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            dimensionEditText.setText(args.filter.dimension)
            typeEditText.setText(args.filter.type)

            dimensionEditText.addTextChangedListener { text ->
                dimension = text.toString()
            }

            typeEditText.addTextChangedListener { text ->
                type = text.toString()
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
        val filters = LocationFilters(type, dimension, "")
        setFragmentResult(
            Constants.REQUEST_KEY_LOCATION,
            bundleOf(Constants.EXTRA_KEY_LOCATION to filters)
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

