package com.pixelperfectsoft.tcg_nexus.ui.play

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.GameBackgroundImage

@Composable
fun Fourplayers(navController: NavController, life: Int) {
    GameBackgroundImage()
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
                playermode = 4
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
                playermode = 4
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
                playermode = 4
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
                playermode = 4
            )
        }
    }
}