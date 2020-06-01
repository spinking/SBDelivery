package studio.eyesthetics.sbdelivery.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class/*,
    NetworkModule::class,
    LocaleModule::class,
    AuthModule::class,
    RepositoryModule::class,
    PreferencesModule::class,
    RepositoryModule::class,
    DatabaseModule::class*/])
interface AppComponent  {
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
    //fun inject(fragment: LoginFragment)
}