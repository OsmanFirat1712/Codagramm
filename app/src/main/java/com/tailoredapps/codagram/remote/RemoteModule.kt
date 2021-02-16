package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.GlobalAppData
import com.google.gson.Gson
import com.tailoredapps.codagram.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val remoteModule = module { single { provideGson() }
    single { provideApi(globalAppData = get(), gson = get(), /*authInterceptor = get()*/) } }

private fun provideGson():Gson = Gson()

private fun provideApi(globalAppData: GlobalAppData,gson:Gson,/*authInterceptor: AuthInterceptor*/): CodagramApi = Retrofit.Builder()
    .apply {
        baseUrl(globalAppData.baseUrl)
        addConverterFactory(GsonConverterFactory.create(gson))
            .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
/*
                .addInterceptor(authInterceptor)
*/
                .build()
        )
    }
    .build()
    .create(CodagramApi::class.java)
