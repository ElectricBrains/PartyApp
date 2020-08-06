package com.example.myapplication.tools.di

import android.app.Application
import com.example.myapplication.repositories.JSonPartyRepository
import com.example.myapplication.repositories.interfaces.IPartyRepository
import com.example.myapplication.tools.contacts.ContactProvider
import com.example.myapplication.tools.interfaces.IContactProvider
import com.example.myapplication.tools.interfaces.IPlaceResolver
import com.example.myapplication.tools.map.PlaceResolver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule () {
    @Provides
    @Singleton
    fun providePartyRepo(application: Application, contactProvider: IContactProvider): IPartyRepository {
        return JSonPartyRepository(application, contactProvider)
    }

    @Provides
    @Singleton
    fun providePlaceResolver(application: Application): IPlaceResolver {
        return PlaceResolver(application)
    }

    @Provides
    @Singleton
    fun provideContactProvider(application: Application): IContactProvider {
        return ContactProvider(application)
    }
}