package com.example.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Party
import com.example.myapplication.models.PlaceCoordinates
import com.example.myapplication.repositories.interfaces.IPartyRepository
import com.example.myapplication.tools.interfaces.IPlaceResolver
import com.example.myapplication.tools.map.PlaceResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PartyPlaceViewModel(private val repository: IPartyRepository,
                          private val placeResolver: IPlaceResolver,
                          private val partyId: Int): ViewModel() {
    val partyName = MutableLiveData<String>()
    val partyPlaceName = MutableLiveData<String>()
    val dataLoadingCompete = MutableLiveData<Pair<String, PlaceCoordinates>>()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            val party = repository.getPartyById(partyId)
            partyName.postValue(party.name)
            partyPlaceName.postValue("${party.place.name} ${party.place.address}")
            val coordinates = placeResolver.resolve(party.place)
            coordinates?.let {
                dataLoadingCompete.postValue(Pair(party.place.address, coordinates))
            }
        }
    }


}
