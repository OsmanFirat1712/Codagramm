package com.tailoredapps.codagram.ui.loginscreen
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginscreenModule = module {
    fragment { LoginFragment() }

    viewModel { LoginViewModel(codagramApi = get(), context = androidContext()) }
}