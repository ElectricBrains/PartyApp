package com.example.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repositories.interfaces.IPartyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PartyListViewModel (private val repository: IPartyRepository) : ViewModel() {
    val list = MutableLiveData<List<PartyViewModel>>()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            list.postValue(
                repository.getAvailableParties().map { party ->
                    PartyViewModel(party)
                })
        }
    }
}