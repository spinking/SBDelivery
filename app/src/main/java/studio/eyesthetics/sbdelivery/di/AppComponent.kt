package studio.eyesthetics.sbdelivery.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.di.modules.*
import studio.eyesthetics.sbdelivery.ui.MainActivity
import studio.eyesthetics.sbdelivery.ui.auth.*
import studio.eyesthetics.sbdelivery.ui.basket.BasketFragment
import studio.eyesthetics.sbdelivery.ui.categories.CategoriesFragment
import studio.eyesthetics.sbdelivery.ui.categories.category.CategoryFragment
import studio.eyesthetics.sbdelivery.ui.dish.DishFragment
import studio.eyesthetics.sbdelivery.ui.dish.ReviewDialogFragment
import studio.eyesthetics.sbdelivery.ui.favorites.FavoritesFragment
import studio.eyesthetics.sbdelivery.ui.home.HomeFragment
import studio.eyesthetics.sbdelivery.ui.menu.MenuFragment
import studio.eyesthetics.sbdelivery.ui.registrationorder.RegistrationOrderFragment
import studio.eyesthetics.sbdelivery.ui.search.SearchFragment
import studio.eyesthetics.sbdelivery.ui.splash.SplashActivity
import studio.eyesthetics.sbdelivery.workers.SyncWorker
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        MapperModule::class,
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
    fun inject(activity: SplashActivity)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: RecoveryPasswordFragment)
    fun inject(fragment: RecoveryCodeFragment)
    fun inject(fragment: RecoveryNewPasswordFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: MenuFragment)
    fun inject(fragment: CategoriesFragment)
    fun inject(fragment: CategoryFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: DishFragment)
    fun inject(fragment: ReviewDialogFragment)
    fun inject(fragment: BasketFragment)
    fun inject(fragment: RegistrationOrderFragment)

    //workers
    fun inject(worker: SyncWorker)
}