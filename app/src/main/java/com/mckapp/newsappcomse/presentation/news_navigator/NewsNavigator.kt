package com.mckapp.newsappcomse.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.mckapp.newsappcomse.R
import com.mckapp.newsappcomse.domain.model.Article
import com.mckapp.newsappcomse.presentation.bookmark.BookmarkScreen
import com.mckapp.newsappcomse.presentation.bookmark.BookmarkViewModel
import com.mckapp.newsappcomse.presentation.details.DetailEvents
import com.mckapp.newsappcomse.presentation.details.DetailScreen
import com.mckapp.newsappcomse.presentation.details.DetailsViewModel
import com.mckapp.newsappcomse.presentation.home.HomeScreen
import com.mckapp.newsappcomse.presentation.home.HomeViewModel
import com.mckapp.newsappcomse.presentation.nav_graph.Route
import com.mckapp.newsappcomse.presentation.news_navigator.components.BottomNavigationItem
import com.mckapp.newsappcomse.presentation.news_navigator.components.NewsBottomNavigation
import com.mckapp.newsappcomse.presentation.search.SearchScreen
import com.mckapp.newsappcomse.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(R.drawable.ic_home, "Home"),
            BottomNavigationItem(R.drawable.ic_search, "Search"),
            BottomNavigationItem(R.drawable.ic_bookmark, "Bookmark")
        )
    }

    val navController = rememberNavController()
    //burada navigation işlemi olduğunda değer değişecek ve recomposition durumu tetiklenecektir.
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    //key1 gibi bir anahtar belirtmek, remember'ın bellekteki önbelleğini yönetirken dikkate alınacak özel
    // bir değeri ifade eder. Eğer backStackState değişirse (örneğin navController.currentBackStackEntryAsState().value
    // değeri güncellenirse), remember'ın içindeki kod bloğu tekrar çalıştırılır ve selectedItem değeri güncellenir.
    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookMarkScreen.route -> 2
            else -> 0
        }
    }
    //Hangi ekranlarda bottomnav gözükmesini istediğimizi belirttik
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookMarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTap(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTap(
                                navController = navController,
                                route = Route.BookMarkScreen.route
                            )
                        }
                    })
            }
        }
    ) { paddingValues ->

        val bottomPadding = paddingValues.calculateBottomPadding()
        NavHost(
            modifier = Modifier.padding(bottom = bottomPadding),
            navController = navController,
            startDestination = Route.HomeScreen.route
        )
        {
            composable(route = Route.HomeScreen.route) {
                val viewmodel: HomeViewModel = hiltViewModel()
                val articles = viewmodel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTap(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewmodel: SearchViewModel = hiltViewModel()
                val state = viewmodel.state.value
                SearchScreen(
                    state = state,
                    event = viewmodel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            article = it
                        )
                    }
                )
            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                //Buranın çalışma mantığı su sekilde kullanıcı add bookmark yaptığı zaman upster methodu çalışıyor giriyor
                //Orada işlemler oluyor orası bittikten sonra gelip buraya bakıyor . SideEffect değişkeni boş olmadığı için if çalışıyor
                //Ve en son sideEffect değişkenini burada nula çekiyoruz.
                //Her ekleme silme işleminde bu blok en son olarak çalışacaktır !
                if (viewModel.sideEffect?.isNotEmpty() == true){
                    Toast.makeText(LocalContext.current,viewModel.sideEffect,Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailEvents.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let {
                        DetailScreen(
                            article = it,
                            event = viewModel::onEvent,
                            navigateUp = {
                                navController.navigateUp()
                            }
                        )
                    }
            }

            composable(route = Route.BookMarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetail = {
                        navigateToDetails(
                            navController = navController,
                            article = it
                        )
                    }
                )
            }
        }
    }
}

fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
                //saveState = true:
                //popUpTo ile belirlenen noktaya kadar olan fragment/state'leri temizleme işlemi sırasında, bu fragmentlerin durumlarını (state) kaydetmeyi sağlar. Bu sayede fragmentlerin içeriği kaybolmaz
            }
            restoreState = true
            //Yönlendirme işlemi sonrasında eski fragment durumlarını geri yüklemeyi sağlar. Bu sayede kullanıcı navigasyon geçmişine geri döndüğünde fragmentlerin eski durumlarını görebilir.
            launchSingleTop = true
            //Hedef rotaya yönlendirme işlemi sırasında, eğer hedef rota zaten en üstteyse (mevcut olan), yeni bir instance oluşturmak yerine mevcut instance'ı kullanmayı sağlar. Bu, bir sayfanın tekrar tekrar açılmasını engeller.
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}