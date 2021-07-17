package com.noemi.android.cocktails.app

import android.app.Application
import com.noemi.android.cocktails.di.AppComponent
import com.noemi.android.cocktails.di.AppModule
import com.noemi.android.cocktails.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
        component.inject(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}