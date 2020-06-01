package studio.eyesthetics.sbdelivery

import android.app.Application
import studio.eyesthetics.sbdelivery.di.AppComponent

class App : Application(), AppComponent.ComponentProvider {

    override lateinit var appComponent: AppComponent

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        appComponent.inject(this)
    }
}