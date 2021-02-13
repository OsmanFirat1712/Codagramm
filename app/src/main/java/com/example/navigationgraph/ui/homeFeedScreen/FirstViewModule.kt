package com.example.navigationgraph.ui.homeFeedScreen

import com.example.navigationgraph.ui.HomeFeedAdapter
import com.example.navigationgraph.ui.FirstScreen
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val firstViewModule = module {
    factory { HomeFeedAdapter() }
    fragment { FirstScreen() }
    viewModel { HomeFeedViewModel(codagramApi = get()) }
}
