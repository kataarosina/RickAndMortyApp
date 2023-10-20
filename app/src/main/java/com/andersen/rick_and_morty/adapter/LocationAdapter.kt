package com.andersen.rick_and_morty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rick_and_morty.databinding.ItemLocationBinding
import com.andersen.rick_and_morty.domain.model.Location

class LocationAdapter(
    context: Context,
    private val onItemClicked: (Location) -> Unit,
) : ListAdapter<Location, LocationViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            binding = ItemLocationBinding.inflate(layoutInflater, parent, false)
        )

    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class LocationViewHolder(
    private val binding: ItemLocationBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Location, onItemClicked: (Location) -> Unit) {
        with(binding) {

            name.text = item.name
            type.text = "type: ${item.type}"
            dimension.text = "dimension: ${item.dimension}"
        }

        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}