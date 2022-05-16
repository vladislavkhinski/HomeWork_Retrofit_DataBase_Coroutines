package com.gmail.vladkhinski.homework_app.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.gmail.vladkhinski.homework_app.databinding.ItemCharacterBinding
import com.gmail.vladkhinski.homework_app.model.Characters

class UserViewHolder(
    private val binding: ItemCharacterBinding,
    private val onUserClicked: (Characters) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: Characters) {
        with(binding) {
            image.load(user.img) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            textName.text = user.name

            root.setOnClickListener {
                onUserClicked(user)
            }
        }
    }
}