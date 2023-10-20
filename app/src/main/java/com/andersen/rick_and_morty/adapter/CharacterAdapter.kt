package com.andersen.rick_and_morty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andersen.rick_and_morty.databinding.ItemCharacterBinding
import com.andersen.rick_and_morty.domain.model.Character

class CharacterAdapter(
    context: Context,
    private val onItemClicked: (Character) -> Unit,
) : ListAdapter<Character, CharacterViewHolder>(DIFF_UTIL) {
    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
        )

    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked)
    }


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Character, onItemClicked: (Character) -> Unit) {
        with(binding) {
            imageCharacter.load(item.image)
            name.text = item.name
            species.text = "species: ${item.species}"
            type.text = "type: ${item.type}"
            status.text = "status: ${item.status}"
            gender.text = "gender: ${item.gender}"
        }


        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}
