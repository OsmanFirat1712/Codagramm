package com.tailoredapps.codagram.remote

import android.content.Context
import com.tailoredapps.codagram.GlobalAppData
import com.google.gson.Gson

import com.tailoredapps.codagram.FirebaseUserIdTokenInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val remoteModule = module { single { provideGson()}
    single { provideApi(globalAppData = get(), gson = get(),  context = androidContext())} }

private fun provideGson():Gson = Gson()


private fun provideApi(globalAppData: GlobalAppData,gson:Gson,context: Context): CodagramApi = Retrofit.Builder()
    .apply {
        baseUrl(globalAppData.baseUrl)
        addConverterFactory(GsonConverterFactory.create(gson))
            .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(FirebaseUserIdTokenInterceptor())
                .build()
        )
    }
    .build()
    .create(CodagramApi::class.java)
