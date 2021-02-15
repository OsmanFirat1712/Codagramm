package com.tailoredapps.codagram.remote

import com.tailoredapps.codagram.GlobalAppData
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val remoteModule = module { single { provideGson() }
    single { provideApi(globalAppData = get(), gson = get()) } }

private fun provideGson():Gson = Gson()

private fun provideApi(globalAppData: GlobalAppData,gson:Gson): CodagramApi = Retrofit.Builder()
    .apply {
        baseUrl(globalAppData.baseUrl)
        addConverterFactory(GsonConverterFactory.create(Gson()))
    }
    .build()
    .create(CodagramApi::class.java)
