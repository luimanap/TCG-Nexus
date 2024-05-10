package com.pixelperfectsoft.tcg_nexus.ui.play

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyButton
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush


data class Game(
    var numplayers: MutableState<Int>,
    var life: MutableState<Int>,
)


@Composable
fun PlayScreen(navController: NavController) {
    val backcolors = listOf(
        Color.Transparent,
        //Color(250,250,250),
        //Color(250,250,250),
        //Color(225,225,225),
        //Color(225,225,225),
        Color.White,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
        //Color.White,
        //Color.White,
    )
    val game = Game(rememberSaveable {
        mutableIntStateOf(0)
    }, rememberSaveable {
        mutableIntStateOf(0)
    })
    BackgroundImage()
    //var players by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = createGradientBrush(backcolors)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(80.dp))
        Text(text = "Let´s Play The Card Games That You Like!",
            color = MaterialTheme.colorScheme.primary,
            //color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                fontSize = 60.sp,
                //fontFamily = FontFamily.SansSerif

        ))
        Spacer(modifier = Modifier.size(35.dp))
        SelectPlayers(game)
        SelectStartLife(game)
        Spacer(modifier = Modifier.size(30.dp))
        StartButton(navController, game.life.value, game.numplayers.value)
    }
}

@Composable
fun StartButton(navController: NavController, life: Int, players: Int) {
    IconButton( onClick = {
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
            }
        }, colors = IconButtonDefaults.iconButtonColors(
            //contentColor = Color(92, 115, 255),
            containerColor = MaterialTheme.colorScheme.primary

        ), modifier = Modifier.size(100.dp)
    ){
        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "", tint = Color.White, modifier = Modifier
            .fillMaxSize()
            .padding(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStartLife(game: Game) {
    var lifevalue by rememberSaveable { mutableStateOf("") }
    var isexpanded by rememberSaveable { mutableStateOf(false) }

    val elements = listOf("20", "30", "40", "50", "60")
    Column(Modifier.padding(horizontal = 40.dp, vertical = 10.dp)) {
        // Spacer(modifier = Modifier.size(250.dp))
        Text(
            text = "Vidas:", style = TextStyle(
                color = Color.Black,
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
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Black, disabledTextColor = Color.Black
                ))
            DropdownMenu(
                expanded = isexpanded,
                onDismissRequest = { isexpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for (i in elements) {
                    DropdownMenuItem(
                        modifier = Modifier.padding(horizontal = 40.dp),
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
        listOf("2 Players", "3 Players", "4 Players"/*, "5 Players", "6 Players"*/)

    Column(Modifier.padding(horizontal = 40.dp, vertical = 10.dp)) {
        Text(
            text = "Jugadores:", style = TextStyle(
                color = Color.Black,
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
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Black, disabledTextColor = Color.Black
                ))
            DropdownMenu(
                expanded = isexpanded,
                onDismissRequest = { isexpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for (i in elements) {
                    DropdownMenuItem(text = { Text(text = i) },
                        modifier = Modifier.padding(horizontal = 40.dp),
                        onClick = {
                            isexpanded = false
                            playersvalue = i
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

