package com.example.myapplication.models

data class Party (
    val id: Int,
    val name: String,
    val partyPhotoUrl: String,
    val eventOwner: Contact?,
    val additionalVisitors: List<Contact>,
    val place: Place
)