package studio.eyesthetics.sbdelivery.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.di.modules.AppModule
import studio.eyesthetics.sbdelivery.di.modules.NetworkModule
import studio.eyesthetics.sbdelivery.di.modules.PreferencesModule
import studio.eyesthetics.sbdelivery.di.modules.RepositoryModule
import studio.eyesthetics.sbdelivery.ui.MainActivity
import studio.eyesthetics.sbdelivery.ui.auth.LoginFragment
import studio.eyesthetics.sbdelivery.ui.auth.RegistrationFragment
import studio.eyesthetics.sbdelivery.ui.home.HomeFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        PreferencesModule::class
    ]
)
interface AppComponent {
    fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    interface ComponentProvider {
        val appComponent: AppComponent
    }

    //activity, fragment
    fun inject(activity: MainActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: HomeFragment)
}