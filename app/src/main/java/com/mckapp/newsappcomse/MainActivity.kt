package com.mckapp.newsappcomse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.mckapp.newsappcomse.domain.usecases.AppEntryUseCases
import com.mckapp.newsappcomse.presentation.nav_graph.NavGraph
import com.mckapp.newsappcomse.presentation.onboarding.OnBoardingScreen
import com.mckapp.newsappcomse.presentation.onboarding.OnBoardingViewModel
import com.mckapp.newsappcomse.ui.theme.NewsAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                //splash screenin ekranda gösterilip gösterilmeyeceği koşulunu veriyoruz
                mainViewModel.splashCondition
            }
        }

        setContent {
            NewsAppComposeTheme {
                Box(modifier = Modifier.background(color=MaterialTheme.colorScheme.background)){
                    val startDestination = mainViewModel.startDestination
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}
