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
fun Threeplayers(life: Int) {
    GameBackgroundImage()
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (p1, p2, p3) = createRefs()
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
                playermode = 3
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
                playermode = 3
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
                playermode = 3
            )
        }
    }
}