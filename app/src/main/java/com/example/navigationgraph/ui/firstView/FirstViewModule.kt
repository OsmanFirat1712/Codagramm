package com.example.navigationgraph.ui.firstView

import com.example.navigationgraph.ui.CountryAdapter
import com.example.navigationgraph.ui.FirstScreen
import com.example.navigationgraph.ui.FirstScreenViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val firstViewModule = module {
    factory { CountryAdapter() }
    fragment { FirstScreen() }
    viewModel { FirstScreenViewModel(countriesApi = get()) }
}
