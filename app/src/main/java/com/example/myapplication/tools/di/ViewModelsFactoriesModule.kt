package com.example.myapplication.tools.di

import com.example.myapplication.repositories.interfaces.IPartyRepository
import com.example.myapplication.tools.interfaces.IPlaceResolver
import com.example.myapplication.viewmodels.PartyListViewModelFactory
import com.example.myapplication.viewmodels.PartyPlaceViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelsFactoriesModule {
    @Provides
    fun providePartyListViewModelFactory(repository: IPartyRepository): PartyListViewModelFactory {
        return PartyListViewModelFactory(repository)
    }

    @Provides
    fun providePartyDetailsViewModelFactory(repository: IPartyRepository, placeResolver: IPlaceResolver): PartyPlaceViewModelFactory {
        return PartyPlaceViewModelFactory(repository,placeResolver)
    }
}