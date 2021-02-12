package com.example.navigationgraph

import com.example.navigationgraph.remote.remoteModule
import com.example.navigationgraph.ui.firstView.firstViewModule
import org.koin.dsl.module

internal val appModule = module { single { provideGlobalAppData() } }

private fun provideGlobalAppData(): GlobalAppData = GlobalAppData(
    baseUrl = "https://restcountries.eu/rest/v2/"
)

data class GlobalAppData(
    val baseUrl: String = ""
)


internal val appModules = listOf(
    appModule,
    firstViewModule,
    remoteModule
)
