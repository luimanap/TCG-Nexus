package com.pixelperfectsoft.tcg_nexus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.auth.LoginScreen
import com.pixelperfectsoft.tcg_nexus.auth.RegisterScreen
import com.pixelperfectsoft.tcg_nexus.cards.Collection
import com.pixelperfectsoft.tcg_nexus.cards.Decks
import com.pixelperfectsoft.tcg_nexus.home.HomeScreen
import com.pixelperfectsoft.tcg_nexus.play.Fourplayers
import com.pixelperfectsoft.tcg_nexus.play.PlayScreen
import com.pixelperfectsoft.tcg_nexus.play.Threeplayers
import com.pixelperfectsoft.tcg_nexus.play.Twoplayers
import com.pixelperfectsoft.tcg_nexus.profile.Profile

data class MenuItems(
    val icon: Int,
    val textId: Int,
    val path: String,
    val label: String,
)

class NaviActions(private val navController: NavHostController) {
    fun navigateTo(destination: MenuItems) {
        navController.navigate(destination.path) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

val AUTH_MENU_ITEMS =
    listOf(
        MenuItems(
            icon = R.drawable.home,
            textId = R.string.home,
            path = MyAppRoute.HOME,
            label = "Inicio"
        ), MenuItems(
            icon = R.drawable.albums,
            textId = R.string.collection,
            path = MyAppRoute.COLLECTION,
            label = "Colección"
        ), MenuItems(
            icon = R.drawable.albums,
            textId = R.string.collection,
            path = MyAppRoute.ALLCARDS,
            label = "Buscar"
        ), MenuItems(
            icon = R.drawable.cards,
            textId = R.string.decks,
            path = MyAppRoute.DECKS,
            label = "Mazos"
        ), MenuItems(
            icon = R.drawable.dice_icon,
            textId = R.string.play,
            path = MyAppRoute.PLAY,
            label = "Jugar"
        ), MenuItems(
            icon = R.drawable.personcirclesharp,
            textId = R.string.profile,
            path = MyAppRoute.LOGIN,
            label = "Perfil"
        )
    )
val GUEST_MENU_ITEMS =
    listOf(
        MenuItems(
            icon = R.drawable.home,
            textId = R.string.home,
            path = MyAppRoute.HOME,
            label = "Inicio"
        ), MenuItems(
            icon = R.drawable.albums,
            textId = R.string.collection,
            path = MyAppRoute.ALLCARDS,
            label = "Buscar"
        ), MenuItems(
            icon = R.drawable.dice_icon,
            textId = R.string.play,
            path = MyAppRoute.PLAY,
            label = "Jugar"
        ), MenuItems(
            icon = R.drawable.personcirclesharp,
            textId = R.string.profile,
            path = MyAppRoute.LOGIN,
            label = "Perfil"
        )
    )


object MyAppRoute {
    const val LOGIN: String = "login"
    const val HOME = "home"
    const val ALLCARDS = "all_cards"
    const val COLLECTION = "collection"
    const val DECKS = "decks"
    const val PLAY = "play"
    const val PROFILE = "profile"
}


@Composable
fun BottomBarNaviContent(
    navController: NavHostController,
    selectedDestination: String,
    navigateTo: (MenuItems) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = MyAppRoute.HOME
            ) {
                composable("register") {
                    RegisterScreen(navController = navController)
                }
                composable(MyAppRoute.HOME) {
                    HomeScreen(navController = navController)
                }
                composable(MyAppRoute.ALLCARDS) {
                    HomeScreen(navController = navController)
                }
                composable(MyAppRoute.COLLECTION) {
                    Collection(navController = navController)
                }
                composable(MyAppRoute.DECKS) {
                    Decks(navController = navController)
                }
                composable(MyAppRoute.PLAY) {
                    PlayScreen(navController = navController)
                }
                composable("4p30") {
                    Fourplayers(navController = navController, 30)
                }
                composable("4p20") {
                    Fourplayers(navController = navController, 20)
                }
                composable("4p40") {
                    Fourplayers(navController = navController, 40)
                }
                composable("4p50") {
                    Fourplayers(navController = navController, 50)
                }
                composable("4p60") {
                    Fourplayers(navController = navController, 60)
                }
                composable("3p30") {
                    Threeplayers(navController = navController, 30)
                }
                composable("3p20") {
                    Threeplayers(navController = navController, 20)
                }
                composable("3p40") {
                    Threeplayers(navController = navController, 40)
                }
                composable("3p50") {
                    Threeplayers(navController = navController, 50)
                }
                composable("3p60") {
                    Threeplayers(navController = navController, 60)
                }
                composable("2p30") {
                    Twoplayers(navController = navController, 30)
                }
                composable("2p20") {
                    Twoplayers(navController = navController, 20)
                }
                composable("2p40") {
                    Twoplayers(navController = navController, 40)
                }
                composable("2p50") {
                    Twoplayers(navController = navController, 50)
                }
                composable("2p60") {
                    Twoplayers(navController = navController, 60)
                }
                composable(MyAppRoute.LOGIN) {
                    LoginScreen(navController = navController)
                }
                composable(MyAppRoute.PROFILE) {
                    Profile(navController = navController)
                }
            }
            BottomBarNavigation(selectedDestination = selectedDestination, navigateTo = navigateTo)
        }
    }
}

@Composable
fun BottomBarNavigation(selectedDestination: String, navigateTo: (MenuItems) -> Unit) {
    val isAuth = remember { mutableStateOf(true) }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        if (isAuth.value) {
            AUTH_MENU_ITEMS.forEach { destinations ->
                NavigationBarItem(
                    label = { Text(text = destinations.label, fontSize = 12.sp) },
                    selected = selectedDestination == destinations.path,
                    onClick = { navigateTo(destinations) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destinations.icon),
                            contentDescription = stringResource(id = destinations.textId),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
            }
        } else {
            GUEST_MENU_ITEMS.forEach { destinations ->
                NavigationBarItem(
                    label = { Text(text = destinations.label, fontSize = 12.sp) },
                    selected = selectedDestination == destinations.path,
                    onClick = { navigateTo(destinations) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destinations.icon),
                            contentDescription = stringResource(id = destinations.textId),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
            }

        }

    }
}