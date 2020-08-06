package com.example.myapplication.repositories.interfaces

import com.example.myapplication.models.Party

interface IPartyRepository {
    suspend fun getAvailableParties() : List<Party>
    suspend fun getPartyById(id: Int) : Party
}