package com.pixelperfectsoft.tcg_nexus.ui.play

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LifePanel(
    plinitlife: String,
    rotation: String,
    backcolor: Color,
    player: Int,
    playermode: Int,
) {
    val plinitlifeint = plinitlife.trim().toInt()
    val lives = PlayerLives(
        p1life = rememberSaveable { mutableIntStateOf(plinitlifeint) },
        p2life = rememberSaveable { mutableIntStateOf(plinitlifeint) },
        p3life = rememberSaveable { mutableIntStateOf(plinitlifeint) },
        p4life = rememberSaveable { mutableIntStateOf(plinitlifeint) },
    )
    var rotate = 0f
    if (rotation == "left") {
        rotate = 90f
    } else if (rotation == "right") {
        rotate = -90f
    } else if (rotation == "topdown") {
        rotate = 180f
    }
    Card(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backcolor
        ),
    ) {
        when (playermode) {
            //Layout de dos jugadores
            2 -> {
                Row {
                    //Boton de cambiar vidas
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.33f)
                            .background(Color.Transparent)
                            .clickable {
                                when (player) {
                                    //Sumar vida al jugador 1
                                    1 -> lives.p1life.value += 1
                                    //Restar vida al jugador 2
                                    2 -> lives.p2life.value -= 1
                                }
                            })
                    //Mostrar vidas
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        when (player) {
                            //Jugador 1
                            1 -> {
                                Spacer(modifier = Modifier.fillMaxSize(0.25f))
                                Text(
                                    text = lives.p1life.value.toString(),
                                    modifier = Modifier.rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 100.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                Spacer(modifier = Modifier.size(80.dp))
                                Text(
                                    text = "P$player",
                                    modifier = Modifier.rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                            //Jugador 2
                            2 -> {
                                Spacer(modifier = Modifier.fillMaxSize(0.1f))
                                Text(
                                    text = "P$player",
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                Spacer(modifier = Modifier.fillMaxSize(0.2f))
                                Text(
                                    text = lives.p2life.value.toString(),
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 100.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                    //Boton de cambiar vidas
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .clickable {
                                when (player) {
                                    //Restar vida al jugador 1
                                    1 -> lives.p1life.value -= 1
                                    //Sumar vida al jugador 2
                                    2 -> lives.p2life.value += 1
                                }
                            }
                    )
                }
            }
            //Layout de 3 jugadores
            3 -> {
                when (player) {
                    //Jugador 1
                    1 -> Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.4f)
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    lives.p1life.value -= 1
                                })
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(0.4f)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            //Vida del jugador
                            Text(
                                text = lives.p1life.value.toString(),
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 90.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                            //Id del jugador (P1,P2,...)
                            Text(
                                text = "P$player",
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    lives.p1life.value += 1
                                })
                    }
                    //Jugador 2
                    2 -> Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.4f)
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    lives.p2life.value += 1
                                })
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(0.4f)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "P$player",
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                            //Player life
                            Text(
                                text = lives.p2life.value.toString(),
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 90.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    lives.p2life.value -= 1
                                })
                    }
                    //Jugador 3
                    3 -> Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.4f)
                                .background(Color.Transparent)
                                .clickable {
                                    lives.p3life.value += 1
                                })
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "P$player",
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 30.sp
                                )
                            )
                            Text(
                                text = lives.p3life.value.toString(),
                                modifier = Modifier
                                    .rotate(rotate)
                                    .padding(top = 90.dp),
                                style = TextStyle(
                                    fontSize = 90.sp
                                )
                            )
                            Spacer(modifier = Modifier.fillMaxHeight(0.6f))
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    lives.p3life.value -= 1
                                })
                    }
                }
            }
            //Layout para 4 jugadores
            4 -> {
                Column {
                    //Sumamos vida para los jugadores 2 y 4 y restamos para los jugadores 1 y 3
                    Box(modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clickable {
                            when (player) {
                                1 -> lives.p1life.value -= 1
                                2 -> lives.p2life.value += 1
                                3 -> lives.p3life.value -= 1
                                4 -> lives.p4life.value += 1
                            }
                        })
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight(0.4f)
                    ) {
                        when (player) {
                            //Jugadores 1 y 3
                            1, 3 -> {
                                Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                                //Vida
                                Text(
                                    text = if (player == 1) {
                                        lives.p1life.value.toString()
                                    } else {
                                        lives.p3life.value.toString()
                                    },
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 90.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                //Numero de jugador
                                Text(
                                    text = "P$player",
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                            //Jugadores 2 y 4
                            2, 4 -> {
                                Spacer(modifier = Modifier.fillMaxWidth(0.1f))
                                //Numero de jugador
                                Text(
                                    text = "P$player",
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                //Vida
                                Text(
                                    text = if (player == 2) {
                                        lives.p2life.value.toString()
                                    } else {
                                        lives.p4life.value.toString()
                                    },
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 90.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                    //Sumamos vida para los jugadores 1 y 3 y restamos para los jugadores 2 y 4
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clickable {
                            when (player) {
                                1 -> lives.p1life.value += 1
                                2 -> lives.p2life.value -= 1
                                3 -> lives.p3life.value += 1
                                4 -> lives.p4life.value -= 1
                            }
                        })
                }
            }
        }
    }
}






