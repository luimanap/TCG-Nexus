package com.example.tcg_nexus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Games(navController: NavController) {
    BackgroundImage()
    var navController = rememberNavController()
    var players by rememberSaveable {
        mutableStateOf("")
    }
    NavHost(navController = navController, startDestination = "login") {
        composable("2p") { LoginScreen(navController = navController) }
        composable("3p") { RegisterScreen(navController = navController) }
        composable("4p") { HomeScreen(navController = navController) }
        composable("5p") { Games(navController = navController) }
        composable("6p") { Games(navController = navController) }
    }

    SelectPlayers()

}

@Composable
fun Fourplayers(navController: NavController) {
    var p1lives: Int by rememberSaveable {
        mutableStateOf(40)
    }
    var p2lives: Int by rememberSaveable {
        mutableStateOf(40)
    }
    var p3lives: Int by rememberSaveable {
        mutableStateOf(40)
    }
    var p4lives by rememberSaveable {
        mutableStateOf(40)
    }

    Column {
        Row {
            Card {
                Text(text = "${p1lives}")
            }
            Card {
                Text(text = "${p2lives}")
            }
        }
        Row {
            Card {
                Text(text = "${p3lives}")
            }
            Card {
                Text(text = "${p4lives}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPlayers() {
    var defaultvalue by rememberSaveable { mutableStateOf("") }
    var isexpanded by rememberSaveable { mutableStateOf(false) }

    val elements = listOf<String>("2 Players", "3 Players", "4 Players", "5 Players")

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(value = defaultvalue,
            onValueChange = { defaultvalue = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { isexpanded = true }
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = Color.Black, disabledTextColor = Color.Black
            ),
            label = { Text(text = "Jugadores") })
        DropdownMenu(
            expanded = isexpanded,
            onDismissRequest = { isexpanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in elements) {
                DropdownMenuItem(text = { Text(text = i) },
                    onClick = { isexpanded = false; defaultvalue = i })
            }
        }
    }
}