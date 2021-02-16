package com.tailoredapps.codagram.ui.loginscreen

import com.tailoredapps.codagram.ui.FirstScreen
import com.tailoredapps.codagram.ui.HomeFeedAdapter
import com.tailoredapps.codagram.ui.homeFeedScreen.HomeFeedViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginscreenModule = module {
    single { LoginActiviy() }
    viewModel { LoginViewModeL(codagramApi = get()) }
}