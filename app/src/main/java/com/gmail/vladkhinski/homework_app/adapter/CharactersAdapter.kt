package com.gmail.vladkhinski.homework_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gmail.vladkhinski.homework_app.databinding.ItemCharacterBinding
import com.gmail.vladkhinski.homework_app.model.Characters

class CharactersAdapter(
    context: Context,
    private val onUserClicked: (Characters) -> Unit,
) : ListAdapter<Characters, UserViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            binding = ItemCharacterBinding.inflate(layoutInflater, parent, false),
            onUserClicked = onUserClicked
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Characters>() {
            override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
                return (oldItem.name == newItem.name && oldItem.img == newItem.img)
            }
        }
    }
}