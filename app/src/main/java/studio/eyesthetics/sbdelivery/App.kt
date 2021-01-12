package studio.eyesthetics.sbdelivery

import android.app.Application
import studio.eyesthetics.sbdelivery.data.network.NetworkMonitor
import studio.eyesthetics.sbdelivery.di.AppComponent
import studio.eyesthetics.sbdelivery.di.DaggerAppComponent


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

        NetworkMonitor.registerNetworkMonitor(applicationContext)

        appComponent.inject(this)
    }
}