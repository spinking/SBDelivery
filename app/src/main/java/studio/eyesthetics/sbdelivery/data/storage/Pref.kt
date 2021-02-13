package studio.eyesthetics.sbdelivery.data.storage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.squareup.moshi.Moshi
import studio.eyesthetics.sbdelivery.data.delegates.PrefDelegate
import studio.eyesthetics.sbdelivery.data.delegates.PrefLiveDelegate
import studio.eyesthetics.sbdelivery.data.delegates.PrefLiveObjDelegate
import studio.eyesthetics.sbdelivery.data.delegates.PrefObjDelegate
import studio.eyesthetics.sbdelivery.data.models.profile.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Pref @Inject constructor(
    private val context: Context,
    moshi: Moshi
) {
    private fun getSharedPreferences(prefName: String) =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    val mainPrefs by lazy { getSharedPreferences(MAIN_PREFS) }

    val isAuthLive: LiveData<Boolean> by lazy {
        val token by PrefLiveDelegate("accessToken", "", mainPrefs)
        token.map { it.isNotEmpty() }
    }

    var accessToken by PrefDelegate("", mainPrefs)
    var refreshToken by PrefDelegate("", mainPrefs)

    var profile: Profile? by PrefObjDelegate(moshi.adapter(Profile::class.java))
    val profileLive: LiveData<Profile?> by PrefLiveObjDelegate("profile", moshi.adapter(Profile::class.java), mainPrefs)

    companion object {
        private const val MAIN_PREFS = "main_prefs"
    }
}