 package com.tailoredapps.codagram.ui.groupscreen

import com.tailoredapps.codagram.ui.loginscreen.LoginFragment
import com.tailoredapps.codagram.ui.loginscreen.LoginViewModel
import com.tailoredapps.codagram.ui.newStoryScreen.NewStoryFragment
import com.tailoredapps.codagram.ui.newStoryScreen.NewStoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal val groupscreenmodule = module {
    factory { SearchAdapter() }
    factory { GroupDetailsAdapter() }
    factory { GroupInviteAdapter() }
    fragment { GroupFragment() }
    fragment { GroupDetailsFragment()}
    fragment { NewStoryFragment() }
    viewModel { NewStoryViewModel (codagramApi = get()) }
    viewModel { GroupViewModel (codagramApi = get(), context = androidContext()) }
    viewModel { GroupDetailsViewModel (codagramApi = get(), context = androidContext()) }
}