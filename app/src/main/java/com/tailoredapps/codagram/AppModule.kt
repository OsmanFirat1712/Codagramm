package com.tailoredapps.codagram

import com.tailoredapps.codagram.remote.remoteModule
import com.tailoredapps.codagram.ui.homeFeedScreen.firstViewModule
import com.tailoredapps.codagram.ui.loginscreen.loginscreenModule
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
    remoteModule,
    loginscreenModule
)
