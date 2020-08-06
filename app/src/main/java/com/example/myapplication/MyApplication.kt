package com.example.myapplication

import android.app.Application
import com.example.myapplication.tools.di.DaggerIViewModelsComponent
import com.example.myapplication.tools.di.IViewModelsComponent

class MyApplication : Application() {
      val appComponent: IViewModelsComponent by lazy {
          return@lazy DaggerIViewModelsComponent.builder().application(this).build()
      }
}