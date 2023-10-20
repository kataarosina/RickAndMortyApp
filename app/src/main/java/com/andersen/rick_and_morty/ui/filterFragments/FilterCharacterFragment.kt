package com.andersen.rick_and_morty.ui.filterFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.andersen.rick_and_morty.R
import com.andersen.rick_and_morty.databinding.FragmentFilterCharacterBinding
import com.andersen.rick_and_morty.domain.model.ChatacterFilters
import com.andersen.rick_and_morty.domain.model.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterCharacterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterCharacterBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args by navArgs<FilterCharacterFragmentArgs>()

    private var status: String = ""
    private var gender: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentFilterCharacterBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            setFilterStats(R.array.status, statusSpinner)
            setFilterStats(R.array.gender, genderSpinner)
            speciesEditText.setText(args.filter.species)
            setSpinnerValue(statusSpinner, mapToSpinnerStatus(args.filter.status))
            setSpinnerValue(genderSpinner, mapToSpinnerGender(args.filter.gender))


            buttonCancel.setOnClickListener {
                onCancelClicked()
            }
            buttonOk.setOnClickListener {
                onSearchClicked(speciesEditText.text.toString(), typeEditText.text.toString())
            }
        }
    }

    private fun showToast(
        context: Context = requireContext(),
        message: String,
        duration: Int = Toast.LENGTH_LONG,
    ) {
        Toast.makeText(context, message, duration).show()
    }


    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    private fun onSearchClicked(species: String, type: String) {
        val filters = ChatacterFilters(species, type, status, gender, "")
        setFragmentResult(
            Constants.REQUEST_KEY_CHARACTER,
            bundleOf(Constants.EXTRA_KEY_CHARACTER to filters)
        )
        dismiss()
    }

    private fun onCancelClicked() {
        dismiss()
    }

    private fun setSpinnerValue(spinner: Spinner, value: Int) {
        for (i in 0 until spinner.adapter.count) {
            if (i == value) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun setFilterStats(array: Int, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            array,
            R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_text)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (array) {

                    R.array.status -> {
                        val result = when (p2) {
                            0 -> ""
                            1 -> "Alive"
                            2 -> "Dead"
                            3 -> "Unknown"
                            else -> ""
                        }
                        status = result
                    }

                    R.array.gender -> {
                        val result = when (p2) {
                            0 -> ""
                            1 -> "Female"
                            2 -> "Male"
                            3 -> "Genderless"
                            4 -> "Unknown"
                            else -> ""
                        }
                        gender = result

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                showToast(message = getString(R.string.nothing_selected))
            }
        }
    }


    private fun mapToSpinnerStatus(value: String): Int {
        return when (value) {
            "Alive" -> 1
            "Dead" -> 2
            "Unknown" -> 3
            else -> 0
        }
    }

    private fun mapToSpinnerGender(value: String): Int {
        return when (value) {
            "Female" -> 1
            "Male" -> 2
            "Genderless" -> 3
            "Unknown" -> 4
            else -> 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

