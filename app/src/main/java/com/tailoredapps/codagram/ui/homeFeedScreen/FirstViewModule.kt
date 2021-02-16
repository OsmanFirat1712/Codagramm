package com.tailoredapps.codagram.ui.homeFeedScreen

import com.tailoredapps.codagram.ui.HomeFeedAdapter
import com.tailoredapps.codagram.ui.FirstScreen
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val firstViewModule = module {
    factory { HomeFeedAdapter() }
    fragment { FirstScreen() }
    viewModel { HomeFeedViewModel(codagramApi = get()) }
}
