package com.example.myapplication.tools.interfaces

import com.example.myapplication.models.Contact

interface IContactProvider {
    fun getContactList(): List<Contact>
}