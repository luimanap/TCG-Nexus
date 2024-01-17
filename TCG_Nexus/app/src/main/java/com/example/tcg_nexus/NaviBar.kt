package com.example.tcg_nexus

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.tcg_nexus.MenuItems.*
/*@Composable
fun NavigationBar(navHostController: NavHostController){
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val navitem = listOf(Home,Collection,Decks,Games,Profile)
    
    NavHost(navController = navController, startDestination = HomeScreen){

    }


}*/
@Composable
fun Home(){
    Column {
        Text("Home")
    }
}
@Composable
fun Collection(){
    Column {
        Text("Home")
    }
}
@Composable
fun Decks(){
    Column {
        Text("Home")
    }
}
@Composable
fun Games(){
    Column {
        Text("Home")
    }
}
@Composable
fun Profile(){
    Column {
        Text("Home")
    }
}