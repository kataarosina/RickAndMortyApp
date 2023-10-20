package com.andersen.rick_and_morty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andersen.rick_and_morty.databinding.ItemEpisodeBinding
import com.andersen.rick_and_morty.domain.model.Episode


class EpisodeAdapter(
    context: Context,
    private val onItemClicked: (Episode) -> Unit,
) : ListAdapter<Episode, EpisodeViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            binding = ItemEpisodeBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class EpisodeViewHolder(
    private val binding: ItemEpisodeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Episode, onItemClicked: (Episode) -> Unit) {
        with(binding) {
            name.text = item.name
            episode.text = "episode: ${item.episode}"
            airDate.text = "air date: ${item.airDate}"
        }

        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}