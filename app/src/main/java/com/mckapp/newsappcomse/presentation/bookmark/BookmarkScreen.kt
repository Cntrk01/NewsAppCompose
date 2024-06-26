package com.mckapp.newsappcomse.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.mckapp.newsappcomse.R
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.presentation.common.ArticleList
import com.mckapp.newsappcomse.presentation.nav_graph.Route
import com.mckapp.newsappcomse.utils.Dimens.mediumPadding1

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetail: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = mediumPadding1, start = mediumPadding1, end = mediumPadding1)
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title)
        )

        Spacer(modifier = Modifier.height(mediumPadding1))

        state.articles?.let { article ->
            ArticleList(
                articles = article,
                onClick = { navigateToDetail(it) })
        }
    }
}