package com.pixelperfectsoft.tcg_nexus.ui.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import com.pixelperfectsoft.tcg_nexus.ui.GameBackgroundImage

@Composable
fun Twoplayers(life: Int) {
    GameBackgroundImage()
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (p1, p2) = createRefs()

        //Jugador 1
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
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
                playermode = 2
            )
        }
        //Jugador 2
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .constrainAs(p2) {
                top.linkTo(p1.bottom)
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