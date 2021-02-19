package com.tailoredapps.codagram

import android.content.Context
import com.tailoredapps.codagram.remote.SessionManager
import com.tailoredapps.codagram.remote.remoteModule
import com.tailoredapps.codagram.ui.groupscreen.groupscree
import com.tailoredapps.codagram.ui.groupscreen.groupscreenmodule
import com.tailoredapps.codagram.ui.homeFeedScreen.firstViewModule
import com.tailoredapps.codagram.ui.loginscreen.loginscreenModule
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.immutableListOf
import org.koin.dsl.module

internal val appModule = module { single { provideGlobalAppData() } }

private fun provideGlobalAppData(): GlobalAppData = GlobalAppData(
    baseUrl = "https://codagram.tailored-apps.com/api/"
)

data class GlobalAppData(
    val baseUrl: String = ""
)

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("X-FIREBASE-TOKEN",  " $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}

internal val appModules = immutableListOf(
    appModule,
    firstViewModule,
    remoteModule,
    loginscreenModule,
    groupscreenmodule,
    groupscree
)
