package studio.eyesthetics.sbdelivery.di.modules

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import studio.eyesthetics.sbdelivery.data.storage.Pref

@Module
class PreferencesModule {
    @Provides
    fun providePref(
        context: Context,
        moshi: Moshi
    ): Pref = Pref(context, moshi)
}