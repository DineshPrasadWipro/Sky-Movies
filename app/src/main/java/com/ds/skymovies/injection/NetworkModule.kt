package com.ds.skymovies.injection

import com.ds.skymovies.network.NetworkInterface
import com.ds.skymovies.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // Provide Gson
    single<Gson> {
        GsonBuilder().create()
    }

    // Provide HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    // Provide CacheInterceptor
    val networkCacheInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.MINUTES)
            .build()

        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }

    // Provide OkHttpClient with 10 minute data cache
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .cache(Cache(androidContext().cacheDir, 10 * 1024 * 1024))
            .addNetworkInterceptor(networkCacheInterceptor)
            .build()
    }


    // Provide Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    // Provide NetworkInterface
    single<NetworkInterface> {
        get<Retrofit>().create(NetworkInterface::class.java)
    }

}
