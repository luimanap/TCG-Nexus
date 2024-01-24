package com.pixelperfectsoft.tcg_nexus.play

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.GameBackgroundImage
import com.pixelperfectsoft.tcg_nexus.R

data class PlayerLives(
    var p1life: MutableState<Int>,
    var p2life: MutableState<Int>,
    var p3life: MutableState<Int>,
    var p4life: MutableState<Int>,
    var p5life: MutableState<Int>,
    var p6life: MutableState<Int>,
)

@Composable
fun LifePanel(
    plinitlife: String,
    rotation: String,
    backcolor: Color,
    player: Int,
    players: Int,
) {
    val plinitlifeint = plinitlife.trim().toInt()
    val lives: PlayerLives = PlayerLives(
        p1life = rememberSaveable {
            mutableIntStateOf(plinitlifeint)
        },
        p2life = rememberSaveable {
            mutableIntStateOf(plinitlifeint)
        },
        p3life = rememberSaveable {
            mutableIntStateOf(plinitlifeint)
        },
        p4life = rememberSaveable {
            mutableIntStateOf(plinitlifeint)
        },
        p5life = rememberSaveable {
            mutableIntStateOf(plinitlifeint)
        },
        p6life = rememberSaveable {
            mutableIntStateOf(plinitlifeint)
        },
    )
    val context = LocalContext.current

    var rotate: Float = 0f
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
        when (players) {
            //2 players layout
            2 -> {
                Row {
                    //Life change button
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.33f)
                            .background(Color.Transparent)
                            .clickable {
                                when (player) {
                                    //Player 1 life++
                                    1 -> lives.p1life.value += 1
                                    //Player 2 life--
                                    2 -> lives.p2life.value -= 1
                                }
                            })
                    //Life display
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        when (player) {
                            //Player 1 in 2 Players
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
                            //Player 2 in 2 Players
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
                    //Life change button
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .clickable {
                                when (player) {
                                    //Player 1 life--
                                    1 -> lives.p1life.value -= 1
                                    //Player 2 life++
                                    2 -> lives.p2life.value += 1
                                }
                            }
                    )
                }
            }
            //3 players layout
            3 -> {when(player){
                    //Player 1 in 3 player mode
                    1 -> Column (modifier = Modifier.fillMaxSize()){
                        Box(modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth().background(Color.Transparent).clickable {
                            lives.p1life.value -= 1
                        })
                        Row(modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            //Player life
                            Text(
                                text = lives.p1life.value.toString(),
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 90.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                            //Player id (P1,P2...)
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
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Transparent).clickable {
                            lives.p1life.value += 1
                        })
                    }
                    2 -> Column (modifier = Modifier.fillMaxSize()){
                        Box(modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth().background(Color.Transparent).clickable {
                            lives.p2life.value += 1
                        })
                        Row(modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            //Player id (P1,P2...)
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
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Transparent).clickable {
                            lives.p2life.value -= 1
                        })
                    }
                    3-> Row {
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.4f).background(Color.Transparent).clickable {
                            lives.p3life.value += 1
                        })
                        Column {
                            //Player id (P1,P2...)
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
                                text = lives.p3life.value.toString(),
                                modifier = Modifier
                                    .rotate(rotate),
                                style = TextStyle(
                                    fontSize = 90.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Transparent).clickable {
                            lives.p3life.value -= 1
                        })
                    }
                }
            }
            //4 players layout
            4 -> {
                Column {
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
                            //Player 1 and 3 in 4 Players
                            1, 3 -> {
                                Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                                //Life
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
                                //Player id (P1,P2...)
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
                            //Players 2 and 4 in 4 Players
                            2, 4 -> {
                                Spacer(modifier = Modifier.fillMaxWidth(0.1f))
                                //Life//Player id (P1,P2...)
                                Text(
                                    text = "P$player",
                                    modifier = Modifier
                                        .rotate(rotate),
                                    style = TextStyle(
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                //Life
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
            //5 ->
            //6 ->
        }
    }
}

@Composable
fun Fourplayers(navController: NavController, life: Int) {
    GameBackgroundImage()
    //ConstraintLayout(Modifier.fillMaxSize().background(Color(40,40,40))) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        var (p1, p2, p3, p4) = createRefs()
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "left",
                backcolor = Color(150, 150, 255),
                player = 1,
                players = 4
            )
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p2) {
                start.linkTo(p1.end)
            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "right",
                backcolor = Color(255, 150, 150),
                player = 2,
                players = 4
            )
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p3) {
                top.linkTo(p1.bottom)

            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "left",
                backcolor = Color(150, 255, 150),
                player = 3,
                players = 4
            )
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p4) {
                top.linkTo(p2.bottom)
                start.linkTo(p3.end)

            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "right",
                backcolor = Color(255, 255, 150),
                player = 4,
                players = 4
            )
        }
    }
}


@Composable
fun Threeplayers(navController: NavController, life: Int) {
    BackgroundImage()
    ConstraintLayout(Modifier.fillMaxSize()) {
        var (p1, p2, p3) = createRefs()
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "left",
                backcolor = Color(150, 150, 255),
                player = 1,
                players = 3
            )
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p2) {
                start.linkTo(p1.end)
            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "right",
                backcolor = Color(255, 150, 150),
                player = 2,
                players = 3
            )
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .constrainAs(p3) {
                top.linkTo(p1.bottom)
                end.linkTo(p2.end)
                start.linkTo(p1.start)
            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "",
                backcolor = Color(150, 255, 150),
                player = 3,
                players = 3
            )
        }
    }
}

@Composable
fun Twoplayers(navController: NavController, life: Int) {
    BackgroundImage()
    ConstraintLayout(Modifier.fillMaxSize()) {
        var (p1, p2, tools) = createRefs()

        //Player 1 in 2 player mode
        Box(modifier = Modifier
            .fillMaxHeight(0.45f)
            .fillMaxWidth()
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {

            LifePanel(
                plinitlife = "$life",
                rotation = "topdown",
                backcolor = Color(150, 150, 255),
                player = 1,
                players = 2
            )
        }
        //Tool palette
        Box(
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .fillMaxWidth()
                .constrainAs(tools) {
                    top.linkTo(p1.bottom)
                }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.fillMaxWidth(0.33f))
                    Box {

                        Icon(
                            painter = painterResource(id = R.drawable.dice_icon),
                            contentDescription = "Roll",
                            tint = Color.Black,
                            modifier = Modifier.fillMaxSize(0.5f)
                        )

                        //Image(painter = painterResource(id = R.drawable.dice_icon), contentDescription = "Roll", modifier = Modifier.size(48.dp))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth())
                }
            }
        }
        //Player 2 in 2 player mode
        Box(modifier = Modifier
            .fillMaxHeight(0.45f)
            .fillMaxWidth()
            .constrainAs(p2) {
                top.linkTo(tools.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }) {
            LifePanel(
                plinitlife = "$life",
                rotation = "default",
                backcolor = Color(255, 150, 150),
                player = 2,
                players = 2
            )
        }
    }
}