package com.pixelperfectsoft.tcg_nexus.ui.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.R

@Composable
fun Twoplayers(navController: NavController, life: Int) {
    BackgroundImage()
    ConstraintLayout(Modifier.fillMaxSize()) {
        var (p1, p2, tools) = createRefs()

        //Jugador 1
        Box(modifier = Modifier
            .fillMaxHeight(0.45f)
            .fillMaxWidth()
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {

            LifePanel(plinitlife = "$life",rotation = "topdown",backcolor = Color(150, 150, 255),player = 1,playermode = 2
            )
        }
        //Barra de herramientas
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
        //Jugador 2
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
                playermode = 2
            )
        }
    }
}