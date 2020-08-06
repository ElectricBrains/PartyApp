package com.example.myapplication.viewmodels

import com.example.myapplication.models.Contact
import com.example.myapplication.models.Party

class PartyViewModel(party: Party) {
    val id: Int = party.id
    val name: String = party.name
    val partyPhotoUrl: String = party.partyPhotoUrl
    val eventOwner: Contact? = party.eventOwner
    val additionalVisitors: List<Contact> = party.additionalVisitors
}