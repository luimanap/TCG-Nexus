package com.example.tcg_nexus
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


class NaviActions(private val navController: NavHostController){
    fun navigateTo(destination: MenuItems){
        navController.navigate(destination.path){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

data class MenuItems(
    val icon: Int,
    val textId: Int,
    val path: String
)

val TOP_LEVEL_DESTINATIONS = listOf(
    MenuItems(
        icon = R.drawable.home,
        textId = R.string.home,
        path = MyAppRoute.HOME
    ),
    MenuItems(
        icon = R.drawable.albums,
        textId = R.string.collection,
        path = MyAppRoute.COLLECTION
    ),
    MenuItems(
        icon = R.drawable.cards,
        textId = R.string.decks,
        path = MyAppRoute.DECKS
    ),
    MenuItems(
        icon = R.drawable.dice_icon,
        textId = R.string.play,
        path = MyAppRoute.PLAY
    ),
    MenuItems(
        icon = R.drawable.personcirclesharp,
        textId = R.string.profile,
        path = MyAppRoute.PROFILE
    ),
)

object MyAppRoute{
    const val HOME = "home"
    const val COLLECTION = "collection"
    const val DECKS = "decks"
    const val PLAY = "play"
    const val PROFILE = "profile"
}