package com.example.tcg_nexus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController


data class Game(
    var numplayers: MutableState<Int>,
    var life: MutableState<Int>,
)



@Composable
fun PlayScreen(navController: NavController) {
    val backcolors = listOf<Color>(
        Color.Transparent,
        Color(25, 25, 25, 210),
        Color(25, 25, 25, 210),
        Color(25, 25, 25, 255),
    )
    var game: Game = Game(rememberSaveable {
        mutableIntStateOf(0)
    }, rememberSaveable {
        mutableIntStateOf(0)
    })
    BackgroundImage()
    var players by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = createGradientBrush(backcolors)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(130.dp))
        SelectPlayers(game)
        SelectStartLife(game)
        Spacer(modifier = Modifier.size(8.dp))
        StartButton(navController, game.life.value, game.numplayers.value)
    }
}

@Composable
fun StartButton(navController: NavController, life: Int, players: Int) {
    MyButton(text = "¡A Jugar!", onclick = {
        when (players) {
            2 -> when (life) {
                20 -> navController.navigate("2p20")
                30 -> navController.navigate("2p30")
                40 -> navController.navigate("2p40")
                50 -> navController.navigate("2p50")
                60 -> navController.navigate("2p60")
            }

            3 -> when (life) {
                20 -> navController.navigate("3p20")
                30 -> navController.navigate("3p30")
                40 -> navController.navigate("3p40")
                50 -> navController.navigate("3p50")
                60 -> navController.navigate("3p60")
            }

            4 -> when (life) {
                20 -> navController.navigate("4p20")
                30 -> navController.navigate("4p30")
                40 -> navController.navigate("4p40")
                50 -> navController.navigate("4p50")
                60 -> navController.navigate("4p60")

            }

            5 -> when (life) {
                20 -> navController.navigate("5p20")
                30 -> navController.navigate("5p30")
                40 -> navController.navigate("5p40")
                50 -> navController.navigate("5p50")
                60 -> navController.navigate("5p60")
            }

            6 -> when (life) {
                20 -> navController.navigate("6p20")
                30 -> navController.navigate("6p30")
                40 -> navController.navigate("6p40")
                50 -> navController.navigate("6p50")
                60 -> navController.navigate("6p60")
            }
        }
    }, containercolor = Color(92, 115, 255))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStartLife(game: Game) {
    var lifevalue by rememberSaveable { mutableStateOf("") }
    var isexpanded by rememberSaveable { mutableStateOf(false) }

    val elements = listOf<String>("20", "30", "40", "50", "60")
    Column(Modifier.padding(40.dp)) {
        // Spacer(modifier = Modifier.size(250.dp))
        Text(
            text = "Vidas:", style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(4.dp)
        )
        Column {
            OutlinedTextField(value = lifevalue,
                onValueChange = { lifevalue = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable { isexpanded = true }
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.White, disabledTextColor = Color.White
                ))
            DropdownMenu(
                expanded = isexpanded,
                onDismissRequest = { isexpanded = false },
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {
                for (i in elements) {
                    DropdownMenuItem(
                        text = { Text(text = i) },
                        onClick = {
                            isexpanded = false; lifevalue = i
                            game.life.value = lifevalue.trim().toInt()
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPlayers(game: Game) {
    var playersvalue by rememberSaveable { mutableStateOf("") }
    var isexpanded by rememberSaveable { mutableStateOf(false) }
    val elements =
        listOf<String>("2 Players", "3 Players", "4 Players", "5 Players", "6 Players")

    Column(Modifier.padding(40.dp)) {
        Text(
            text = "Jugadores:", style = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(4.dp)
        )
        Column {
            OutlinedTextField(value = playersvalue,
                onValueChange = { playersvalue = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable { isexpanded = true }
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.White, disabledTextColor = Color.White
                ))
            DropdownMenu(
                expanded = isexpanded,
                onDismissRequest = { isexpanded = false },
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp)
                    .fillMaxWidth()
            ) {
                for (i in elements) {
                    DropdownMenuItem(text = { Text(text = i) },
                        onClick = {
                            isexpanded = false; playersvalue = i;
                            when (playersvalue) {
                                "2 Players" -> game.numplayers.value = 2
                                "3 Players" -> game.numplayers.value = 3
                                "4 Players" -> game.numplayers.value = 4
                                "5 Players" -> game.numplayers.value = 5
                                "6 Players" -> game.numplayers.value = 6
                            }
                        }
                    )
                }
            }
        }
    }
}

