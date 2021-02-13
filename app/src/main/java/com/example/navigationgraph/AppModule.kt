package com.example.navigationgraph

import com.example.navigationgraph.remote.remoteModule
import com.example.navigationgraph.ui.homeFeedScreen.firstViewModule
import okhttp3.internal.immutableListOf
import org.koin.dsl.module

internal val appModule = module { single { provideGlobalAppData() } }

private fun provideGlobalAppData(): GlobalAppData = GlobalAppData(
    baseUrl = "https://codagram.tailored-apps.com/"
)

data class GlobalAppData(
    val baseUrl: String = ""
)


internal val appModules = immutableListOf(
    appModule,
    firstViewModule,
    remoteModule
)
