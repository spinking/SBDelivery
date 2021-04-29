package studio.eyesthetics.sbdelivery.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import studio.eyesthetics.sbdelivery.BuildConfig
import studio.eyesthetics.sbdelivery.data.network.*
import studio.eyesthetics.sbdelivery.data.network.interceptors.*
import studio.eyesthetics.sbdelivery.data.storage.Pref
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class CoroutineScopeIO

    @Provides
    fun provideTokenInterceptor(
        pref: Pref
    ) = TokenInterceptor(pref)

    @Provides
    fun provideTokenAuthenticator(
        authApi: IAuthApi,
        pref: Pref
    ) = TokenAuthenticator(authApi, pref)

    @Provides
    @Singleton
    fun provideJsonConverter(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Provides
    fun provideNetworkStatusInterceptor() = NetworkStatusInterceptor()

    @Provides
    fun provideErrorStatusInterceptor(
        moshi: Moshi,
        pref: Pref
    ) = ErrorStatusInterceptor(moshi, pref)

    @Provides
    fun provideModifierInterceptor(
        pref: Pref
    ) = ModifierInterceptor(pref)

    @Provides
    @Singleton
    @Named(WITHOUT_AUTH_CLIENT)
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
        errorStatusInterceptor: ErrorStatusInterceptor,
        modifierInterceptor: ModifierInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(errorStatusInterceptor)
            .addInterceptor(modifierInterceptor)
            .build()

    @Provides
    @Named(AUTH_CLIENT)
    fun provideAuthOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
        errorStatusInterceptor: ErrorStatusInterceptor,
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(errorStatusInterceptor)
            .authenticator(tokenAuthenticator)
            .build()

    @Provides
    @Singleton
    @Named(WITHOUT_AUTH_RETROFIT)
    fun provideRetrofit(moshi: Moshi, @Named(WITHOUT_AUTH_CLIENT) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Named(AUTH_RETROFIT)
    fun provideAuthRetrofit(moshi: Moshi, @Named(AUTH_CLIENT) okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideCategoryApi(@Named(WITHOUT_AUTH_RETROFIT) retrofit: Retrofit): ICategoryApi =
        retrofit.create(ICategoryApi::class.java)

    @Provides
    fun provideDishesApi(@Named(WITHOUT_AUTH_RETROFIT) retrofit: Retrofit): IDishesApi =
        retrofit.create(IDishesApi::class.java)

    @Provides
    fun provideFavoriteApi(@Named(AUTH_RETROFIT) retrofit: Retrofit): IFavoriteApi =
        retrofit.create(IFavoriteApi::class.java)

    @Provides
    fun provideAuthApi(@Named(WITHOUT_AUTH_RETROFIT) retrofit: Retrofit): IAuthApi =
        retrofit.create(IAuthApi::class.java)

    @Provides
    fun provideReviewApi(@Named(WITHOUT_AUTH_RETROFIT) retrofit: Retrofit): IReviewApi =
        retrofit.create(IReviewApi::class.java)

    @Provides
    fun provideAddReviewApi(@Named(AUTH_RETROFIT) retrofit: Retrofit): IAddReviewApi =
        retrofit.create(IAddReviewApi::class.java)

    companion object {
        private const val WITHOUT_AUTH_CLIENT = "without_auth_client"
        private const val WITHOUT_AUTH_RETROFIT = "without_auth_retrofit"

        private const val AUTH_CLIENT = "auth_client"
        private const val AUTH_RETROFIT = "auth_retrofit"
    }
}