package com.pixelperfectsoft.tcg_nexus.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.ui.UpdateScreen
import com.pixelperfectsoft.tcg_nexus.ui.auth.LoginScreen
import com.pixelperfectsoft.tcg_nexus.ui.auth.RegisterScreen
import com.pixelperfectsoft.tcg_nexus.ui.cards.AllCards
import com.pixelperfectsoft.tcg_nexus.ui.cards.Collection
import com.pixelperfectsoft.tcg_nexus.ui.news.NewsScreen
import com.pixelperfectsoft.tcg_nexus.ui.play.Fourplayers
import com.pixelperfectsoft.tcg_nexus.ui.play.PlayScreen
import com.pixelperfectsoft.tcg_nexus.ui.play.Threeplayers
import com.pixelperfectsoft.tcg_nexus.ui.play.Twoplayers
import com.pixelperfectsoft.tcg_nexus.ui.profile.Profile
import com.pixelperfectsoft.tcg_nexus.ui.settings.SettingsScreen

//Clase para los elementos de la barra
data class MenuItems(
    val icon: Int,
    val textId: Int,
    val path: String,
    val label: String,
)

object MyScreenRoutes {
    const val SETTINGS = "settings"
    const val LOGIN = "login"
    const val HOME = "home"
    const val SEARCH = "search"
    const val COLLECTION = "collection"
    const val UPDATE = "update"
    const val PLAY = "play"
    const val PROFILE = "profile"
    const val REGISTER = "register"
    const val FOURPLAYERS20LIVES = "4p20"
    const val FOURPLAYERS30LIVES = "4p30"
    const val FOURPLAYERS40LIVES = "4p40"
    const val FOURPLAYERS50LIVES = "4p50"
    const val FOURPLAYERS60LIVES = "4p60"
    const val THREEPLAYERS20LIVES = "3p20"
    const val THREEPLAYERS30LIVES = "3p30"
    const val THREEPLAYERS40LIVES = "3p40"
    const val THREEPLAYERS50LIVES = "3p50"
    const val THREEPLAYERS60LIVES = "3p60"
    const val TWOPLAYERS20LIVES = "2p20"
    const val TWOPLAYERS30LIVES = "2p30"
    const val TWOPLAYERS40LIVES = "2p40"
    const val TWOPLAYERS50LIVES = "2p50"
    const val TWOPLAYERS60LIVES = "2p60"
}


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

//Elementos de la barra de navegación que se muestran si el usuario está logueado
val AUTH_MENU_ITEMS = listOf(
    MenuItems(
        icon = R.drawable.personcirclesharp,
        textId = R.string.profile,
        path = MyScreenRoutes.PROFILE,
        label = "Profile"
    ), MenuItems(
        icon = R.drawable.dice_icon,
        textId = R.string.play,
        path = MyScreenRoutes.PLAY,
        label = "Play"
    ), MenuItems(
        icon = R.drawable.searchicon,
        textId = R.string.collection,
        path = MyScreenRoutes.SEARCH,
        label = "Search"
    ), MenuItems(
        icon = R.drawable.albums,
        textId = R.string.collection,
        path = MyScreenRoutes.COLLECTION,
        label = "Collection"
    ), MenuItems(
        icon = R.drawable.news,
        textId = R.string.home,
        path = MyScreenRoutes.HOME,
        label = "News"
    ), MenuItems(
        icon = R.drawable.settings,
        textId = R.string.settings,
        path = MyScreenRoutes.SETTINGS,
        label = "Settings"
    )
)

//Elementos de la barra de navegación que se muestran si el usuario no está logueado
val GUEST_MENU_ITEMS = listOf(
    MenuItems(
        icon = R.drawable.personcirclesharp,
        textId = R.string.login,
        path = MyScreenRoutes.LOGIN,
        label = "Log In"
    ), MenuItems(
        icon = R.drawable.dice_icon,
        textId = R.string.play,
        path = MyScreenRoutes.PLAY,
        label = "Play"
    ), MenuItems(
        icon = R.drawable.searchicon,
        textId = R.string.collection,
        path = MyScreenRoutes.SEARCH,
        label = "Search"
    ), MenuItems(
        icon = R.drawable.news,
        textId = R.string.home,
        path = MyScreenRoutes.HOME,
        label = "News"
    ), MenuItems(
        icon = R.drawable.settings,
        textId = R.string.settings,
        path = MyScreenRoutes.SETTINGS,
        label = "Settings"
    )
)


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomBarNaviContainer(
    navController: NavHostController,
    selectedDestination: String,
    navigateTo: (MenuItems) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = MyScreenRoutes.LOGIN
            ) {
                composable(MyScreenRoutes.SETTINGS) {
                    SettingsScreen(navController)
                }
                composable(MyScreenRoutes.UPDATE) {
                    UpdateScreen(navController)
                }
                composable(MyScreenRoutes.HOME) {
                    NewsScreen()
                }
                composable(MyScreenRoutes.SEARCH) {
                    AllCards(navController = navController)
                }
                composable(MyScreenRoutes.COLLECTION) {
                    Collection(navController = navController)
                }
                composable(MyScreenRoutes.PLAY) {
                    PlayScreen(navController = navController)
                }
                composable(MyScreenRoutes.LOGIN) {
                    LoginScreen(navController = navController)
                }
                composable(MyScreenRoutes.PROFILE) {
                    Profile(navController = navController)
                }
                composable(MyScreenRoutes.REGISTER) {
                    RegisterScreen(navController = navController)
                }
                composable(MyScreenRoutes.FOURPLAYERS20LIVES) {
                    Fourplayers(20)
                }
                composable(MyScreenRoutes.FOURPLAYERS30LIVES) {
                    Fourplayers(30)
                }
                composable(MyScreenRoutes.FOURPLAYERS40LIVES) {
                    Fourplayers(40)
                }
                composable(MyScreenRoutes.FOURPLAYERS50LIVES) {
                    Fourplayers(50)
                }
                composable(MyScreenRoutes.FOURPLAYERS60LIVES) {
                    Fourplayers(60)
                }
                composable(MyScreenRoutes.THREEPLAYERS20LIVES) {
                    Threeplayers(20)
                }
                composable(MyScreenRoutes.THREEPLAYERS30LIVES) {
                    Threeplayers(30)
                }
                composable(MyScreenRoutes.THREEPLAYERS40LIVES) {
                    Threeplayers(40)
                }
                composable(MyScreenRoutes.THREEPLAYERS50LIVES) {
                    Threeplayers(50)
                }
                composable(MyScreenRoutes.THREEPLAYERS60LIVES) {
                    Threeplayers(60)
                }
                composable(MyScreenRoutes.TWOPLAYERS20LIVES) {
                    Twoplayers(20)
                }
                composable(MyScreenRoutes.TWOPLAYERS30LIVES) {
                    Twoplayers(30)
                }
                composable(MyScreenRoutes.TWOPLAYERS40LIVES) {
                    Twoplayers(40)
                }
                composable(MyScreenRoutes.TWOPLAYERS50LIVES) {
                    Twoplayers(50)
                }
                composable(MyScreenRoutes.TWOPLAYERS60LIVES) {
                    Twoplayers(60)
                }

            }
            BottomBarNavigation(selectedDestination = selectedDestination, navigateTo = navigateTo)
        }
    }
}

@Composable
fun BottomBarNavigation(selectedDestination: String, navigateTo: (MenuItems) -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        containerColor = Color(255, 255, 255)
    ) {
        if (user != null) {
            AUTH_MENU_ITEMS.forEach { destinations ->
                NavigationBarItem(
                    label = { Text(text = destinations.label, fontSize = 10.5.sp) },
                    selected = selectedDestination == destinations.path,
                    onClick = { navigateTo(destinations) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destinations.icon),
                            contentDescription = stringResource(id = destinations.textId),
                            modifier = Modifier.size(25.dp)
                        )
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.White
                    )
                )
            }
        } else {
            GUEST_MENU_ITEMS.forEach { destinations ->
                NavigationBarItem(
                    label = { Text(text = destinations.label, fontSize = 11.sp) },
                    selected = selectedDestination == destinations.path,
                    onClick = { navigateTo(destinations) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destinations.icon),
                            contentDescription = stringResource(id = destinations.textId),
                            modifier = Modifier.size(25.dp)
                        )
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(92, 115, 255),
                        indicatorColor = Color.White
                    )
                )
            }

        }

    }
}