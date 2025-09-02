package com.example.ynsdemo

import android.app.Application
import com.example.ynsdemo.di.networkModule
import com.example.ynsdemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule, viewModelModule)
        }
    }
}
