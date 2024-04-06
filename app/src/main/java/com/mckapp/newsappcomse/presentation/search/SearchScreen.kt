package com.mckapp.newsappcomse.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.mckapp.newsappcomse.presentation.common.ArticleList
import com.mckapp.newsappcomse.presentation.common.SearchBar
import com.mckapp.newsappcomse.presentation.nav_graph.Route
import com.mckapp.newsappcomse.utils.Dimens.mediumPadding1

@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigate : (String)->Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = mediumPadding1,
                start = mediumPadding1,
                end = mediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = {
                event(SearchEvent.UpdateSearchQuery(it))
            }, onSearch = {
                event(SearchEvent.SearchNews)
            })

        Spacer(modifier = Modifier.height(mediumPadding1))

        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticleList(
                articles = articles,
                onClick = {
                    navigate(Route.DetailsScreen.route)
            })
        }
    }

}