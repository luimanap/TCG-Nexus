package com.example.tcg_nexus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var totalcards by rememberSaveable { mutableStateOf(0) }
    var estimatedCost by rememberSaveable { mutableStateOf(0) }
    val estimatedcostString by rememberSaveable { mutableStateOf("${estimatedCost} €") }
    BackgroundImage()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(210, 210, 210, 90))
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (info, navi) = createRefs()
            val barrier = createGuidelineFromBottom(0.08f)
            Box(
                modifier = Modifier
                    .fillMaxSize(75f)
                    .zIndex(-2f)
            ) {
                Column {
                    Spacer(modifier = Modifier.size(64.dp))
                    MyCard(
                        text = "Cartas en la colección",
                        number = totalcards.toString(),
                        containercolor = Color(92, 115, 255),
                        contentcolor = Color.White
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    MyCard(
                        text = "Valor estimado",
                        number = estimatedcostString,
                        containercolor = Color.White,
                        contentcolor = Color.Black
                    )
                }

            }
            Box(modifier = Modifier
                .background(Color.White)
                .constrainAs(navi) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    top.linkTo(info.bottom, margin = 50.dp)
                }) {
                Row {
                }
            }
        }
    }
}

@Composable
fun MyCard(text: String, number: String, containercolor: Color, contentcolor: Color) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        colors = CardDefaults.cardColors(
            containerColor = containercolor,
            contentColor = contentcolor
        )

    ) {
        Column {
            Text(
                text = text, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            Text(
                text = number, modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp)
                    .padding(start = 10.dp), fontSize = 50.sp
            )
        }


    }
}
