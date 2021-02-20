package com.tailoredapps.codagram.ui.groupscreen

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
internal val groupscree = module {
    factory { GroupAdapter() }
    fragment { MyGroupScreen() }
    viewModel { MyGroupScreenViewMode (codagramApi = get(), context = androidContext()) }



}