package com.tailoredapps.codagram.ui.homeFeedScreen

import com.tailoredapps.codagram.models.Post
import com.tailoredapps.codagram.ui.HomeFeedScreen
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val firstViewModule = module {
    factory { HomeFeedAdapter(codagramApi = get ()) }
    factory { CommentScreenAdapter(codagramApi = get()) }
    fragment { CommentScreenFragment() }
    fragment { HomeFeedScreen() }
    viewModel { HomeFeedViewModel(codagramApi = get()) }
    viewModel { CommentScreenViewModel(codagramApi = get()) }
}
