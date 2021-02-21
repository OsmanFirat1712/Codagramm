package com.tailoredapps.codagram.ui.groupscreen

import com.tailoredapps.codagram.ui.HomeFeedAdapter
import com.tailoredapps.codagram.ui.loginscreen.LoginFragment
import com.tailoredapps.codagram.ui.loginscreen.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val groupscreenmodule = module {
    factory { SearchAdapter() }
    fragment { GroupFragment() }
    fragment { GroupDetailsFragment()}
    viewModel { GroupViewModel (codagramApi = get(), context = androidContext()) }
    viewModel { GroupDetailsViewModel (codagramApi = get(), context = androidContext()) }
}