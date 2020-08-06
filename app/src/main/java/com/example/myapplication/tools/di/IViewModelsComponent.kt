package com.example.myapplication.tools.di

import android.app.Application
import com.example.myapplication.views.fragments.PartyPlaceFragment
import com.example.myapplication.views.fragments.PartyListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, ViewModelsFactoriesModule::class])
interface IViewModelsComponent {
    fun inject(fragment: PartyListFragment)
    fun inject(fragment: PartyPlaceFragment)

    @Component.Builder
    interface Builder {
        fun build(): IViewModelsComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}