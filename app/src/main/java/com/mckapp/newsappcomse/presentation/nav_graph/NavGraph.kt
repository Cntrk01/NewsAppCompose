package com.mckapp.newsappcomse.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.mckapp.newsappcomse.presentation.onboarding.OnBoardingScreen
import com.mckapp.newsappcomse.presentation.onboarding.OnBoardingViewModel
import com.mckapp.newsappcomse.presentation.search.SearchScreen
import com.mckapp.newsappcomse.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewModel::onEvent) //viewModel.onEvent(it)
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route){
                val viewModel : SearchViewModel = hiltViewModel()
                SearchScreen(state = viewModel.state.value, event = viewModel::onEvent, navigate = {})
            }
        }
    }
}