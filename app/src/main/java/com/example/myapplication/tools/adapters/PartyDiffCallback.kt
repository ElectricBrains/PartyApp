package com.example.myapplication.tools.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.models.Party
import com.example.myapplication.viewmodels.PartyViewModel

class PartyDiffCallback : DiffUtil.ItemCallback<PartyViewModel>() {
        override fun areItemsTheSame(oldItem: PartyViewModel, newItem: PartyViewModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PartyViewModel, newItem: PartyViewModel): Boolean {
            return oldItem.name == newItem.name
        }

    }