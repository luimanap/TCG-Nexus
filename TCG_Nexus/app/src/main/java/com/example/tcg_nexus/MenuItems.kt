package com.example.tcg_nexus

sealed class MenuItems(
    val icon: Int,
    val title: String,
    val path : String
){
    object Home: MenuItems(R.drawable.home, "Home", "home")
    object Collection: MenuItems(R.drawable.albums, "Collection", "collection")
    object Decks: MenuItems(R.drawable.cards, "Decks", "decks")
    object Games: MenuItems(R.drawable.dice_icon, "Games", "games")
    object Profile: MenuItems(R.drawable.personcirclesharp, "Profile", "profile")
}
