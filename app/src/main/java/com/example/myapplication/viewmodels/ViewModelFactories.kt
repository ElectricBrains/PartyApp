package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repositories.interfaces.IPartyRepository
import com.example.myapplication.tools.interfaces.IPlaceResolver


@Suppress("UNCHECKED_CAST")
class PartyListViewModelFactory(private val repository: IPartyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PartyListViewModel(repository) as T
    }

}

@Suppress("UNCHECKED_CAST")
class PartyPlaceViewModelFactory(private val repository: IPartyRepository, private val placeResolver: IPlaceResolver): ViewModelProvider.Factory {
    var placeId = 0
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PartyPlaceViewModel(repository,placeResolver, placeId) as T
    }

}