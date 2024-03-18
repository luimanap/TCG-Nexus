package com.pixelperfectsoft.tcg_nexus.ui.navigation

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
import com.pixelperfectsoft.tcg_nexus.ui.auth.LoginScreen
import com.pixelperfectsoft.tcg_nexus.ui.auth.RegisterScreen
import com.pixelperfectsoft.tcg_nexus.ui.cards.AllCards
import com.pixelperfectsoft.tcg_nexus.ui.cards.Collection
import com.pixelperfectsoft.tcg_nexus.ui.home.HomeScreen
import com.pixelperfectsoft.tcg_nexus.ui.play.Fourplayers
import com.pixelperfectsoft.tcg_nexus.ui.play.PlayScreen
import com.pixelperfectsoft.tcg_nexus.ui.play.Threeplayers
import com.pixelperfectsoft.tcg_nexus.ui.play.Twoplayers
import com.pixelperfectsoft.tcg_nexus.ui.profile.Profile

//Clase para los elementos de la barra
data class MenuItems(
    val icon: Int,
    val textId: Int,
    val path: String,
    val label: String,
)

//Objeto para identificar las rutas de navegación de cada pantalla
object MyScreenRoutes {
    const val LOGIN: String = "login"
    const val HOME = "home"
    const val SEARCH = "search"
    const val COLLECTION = "collection"
    const val DECKS = "decks"
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
        icon = R.drawable.home,
        textId = R.string.home,
        path = MyScreenRoutes.HOME,
        label = "Inicio"
    ), MenuItems(
        icon = R.drawable.searchicon,
        textId = R.string.collection,
        path = MyScreenRoutes.SEARCH,
        label = "Buscar"
    ), MenuItems(
        icon = R.drawable.albums,
        textId = R.string.collection,
        path = MyScreenRoutes.COLLECTION,
        label = "Colección"
    /*), MenuItems(
        icon = R.drawable.cards,
        textId = R.string.decks,
        path = MyScreenRoutes.DECKS,
        label = "Mazos"*/
    ), MenuItems(
        icon = R.drawable.dice_icon,
        textId = R.string.play,
        path = MyScreenRoutes.PLAY,
        label = "Jugar"
    ), MenuItems(
        icon = R.drawable.personcirclesharp,
        textId = R.string.profile,
        path = MyScreenRoutes.LOGIN,
        label = "Perfil"
    )
)

//Elementos de la barra de navegación que se muestran si el usuario no está logueado
val GUEST_MENU_ITEMS = listOf(
    MenuItems(
        icon = R.drawable.home,
        textId = R.string.home,
        path = MyScreenRoutes.HOME,
        label = "Inicio"
    ), MenuItems(
        icon = R.drawable.searchicon,
        textId = R.string.collection,
        path = MyScreenRoutes.SEARCH,
        label = "Buscar"
    ), MenuItems(
        icon = R.drawable.dice_icon,
        textId = R.string.play,
        path = MyScreenRoutes.PLAY,
        label = "Jugar"
    ), MenuItems(
        icon = R.drawable.personcirclesharp,
        textId = R.string.profile,
        path = MyScreenRoutes.LOGIN,
        label = "Login"
    )
)


@Composable
fun BottomBarNaviContainer(
    navController: NavHostController,
    selectedDestination: String,
    navigateTo: (MenuItems) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = MyScreenRoutes.HOME
            ) {
                composable(MyScreenRoutes.HOME) {
                    HomeScreen(navController = navController)
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
                    Fourplayers(navController = navController, 20)
                }
                composable(MyScreenRoutes.FOURPLAYERS30LIVES) {
                    Fourplayers(navController = navController, 30)
                }
                composable(MyScreenRoutes.FOURPLAYERS40LIVES) {
                    Fourplayers(navController = navController, 40)
                }
                composable(MyScreenRoutes.FOURPLAYERS50LIVES) {
                    Fourplayers(navController = navController, 50)
                }
                composable(MyScreenRoutes.FOURPLAYERS60LIVES) {
                    Fourplayers(navController = navController, 60)
                }
                composable(MyScreenRoutes.THREEPLAYERS20LIVES) {
                    Threeplayers(navController = navController, 20)
                }
                composable(MyScreenRoutes.THREEPLAYERS30LIVES) {
                    Threeplayers(navController = navController, 30)
                }
                composable(MyScreenRoutes.THREEPLAYERS40LIVES) {
                    Threeplayers(navController = navController, 40)
                }
                composable(MyScreenRoutes.THREEPLAYERS50LIVES) {
                    Threeplayers(navController = navController, 50)
                }
                composable(MyScreenRoutes.THREEPLAYERS60LIVES) {
                    Threeplayers(navController = navController, 60)
                }
                composable(MyScreenRoutes.TWOPLAYERS20LIVES) {
                    Twoplayers(navController = navController, 20)
                }
                composable(MyScreenRoutes.TWOPLAYERS30LIVES) {
                    Twoplayers(navController = navController, 30)
                }
                composable(MyScreenRoutes.TWOPLAYERS40LIVES) {
                    Twoplayers(navController = navController, 40)
                }
                composable(MyScreenRoutes.TWOPLAYERS50LIVES) {
                    Twoplayers(navController = navController, 50)
                }
                composable(MyScreenRoutes.TWOPLAYERS60LIVES) {
                    Twoplayers(navController = navController, 60)
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
            .height(70.dp),
        containerColor = Color.White
    ) {
        if (user != null) {
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
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.White
                    )
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
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(92, 115, 255),
                        indicatorColor = Color.White
                    )
                )
            }

        }

    }
}