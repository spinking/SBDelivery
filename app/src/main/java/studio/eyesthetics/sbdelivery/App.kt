package studio.eyesthetics.sbdelivery

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import studio.eyesthetics.sbdelivery.data.network.NetworkMonitor
import studio.eyesthetics.sbdelivery.di.AppComponent
import studio.eyesthetics.sbdelivery.di.DaggerAppComponent


class App : Application(), AppComponent.ComponentProvider, ImageLoaderFactory {

    override lateinit var appComponent: AppComponent

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .componentRegistry {
                add(SvgDecoder(applicationContext))
            }
            .build()
    }

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