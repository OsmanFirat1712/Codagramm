package com.tailoredapps.codagram.ui.groupscreen

import com.tailoredapps.codagram.ui.loginscreen.LoginFragment
import com.tailoredapps.codagram.ui.loginscreen.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val groupscreenmodule = module {
    fragment { GroupFragment() }
    viewModel { GroupViewModel (codagramApi = get(), context = androidContext()) }
}