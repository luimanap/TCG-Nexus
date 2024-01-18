package com.example.tcg_nexus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
@Composable
fun LifePanel(text: String, rotation: String, backcolor: Color) {
    var rotate: Float = 0f
    if (rotation == "left") {
        rotate = 90f
    } else if (rotation == "right") {
        rotate = -90f
    } else if(rotation == "topdown"){
        rotate = 180f
    }

    Card (
        Modifier
            .fillMaxSize()
            .padding(8.dp), colors = CardDefaults.cardColors(
            containerColor = backcolor
        )){
        Text(
            text = text,
            modifier = Modifier
                .rotate(rotate)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(fontSize = 100.sp, textAlign = TextAlign.Center)
        )
    }
}
@Composable
fun Fourplayers(navController: NavController, life: Int) {
    var p1lives: Int by rememberSaveable { mutableIntStateOf(life) }
    var p2lives: Int by rememberSaveable { mutableIntStateOf(life) }
    var p3lives: Int by rememberSaveable { mutableIntStateOf(life) }
    var p4lives: Int by rememberSaveable { mutableIntStateOf(life) }

    BackgroundImage()
    ConstraintLayout (Modifier.fillMaxSize()){
        var (p1,p2,p3,p4) = createRefs()
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }){
            LifePanel(text = "$p1lives", rotation = "left", backcolor = Color(150,150,255))
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p2) {
                start.linkTo(p1.end)
            }){
            LifePanel(text = "$p2lives", rotation = "right", backcolor = Color(255,150,150))
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p3) {
                top.linkTo(p1.bottom)

            }){
            LifePanel(text = "$p3lives", rotation = "left", backcolor = Color(150,255,150))
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p4) {
                top.linkTo(p2.bottom)
                start.linkTo(p3.end)

            }){
            LifePanel(text = "$p4lives", rotation = "right", backcolor = Color(255,255,150))
        }
    }
}


@Composable
fun Threeplayers(navController: NavController, life: Int) {
    var p1lives: Int by rememberSaveable { mutableIntStateOf(life) }
    var p2lives: Int by rememberSaveable { mutableIntStateOf(life) }
    var p3lives: Int by rememberSaveable { mutableIntStateOf(life) }

    BackgroundImage()
    ConstraintLayout (Modifier.fillMaxSize()){
        var (p1,p2,p3) = createRefs()
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }){
            LifePanel(text = "$p1lives", rotation = "left", backcolor = Color(150,150,255))
        }
        Box(modifier = Modifier
            .fillMaxSize(0.5f)
            .constrainAs(p2) {
                start.linkTo(p1.end)
            }){
            LifePanel(text = "$p2lives", rotation = "right", backcolor = Color(255,150,150))
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .constrainAs(p3) {
                top.linkTo(p1.bottom)
                end.linkTo(p2.end)
                start.linkTo(p1.start)
            }){
            LifePanel(text = "$p3lives", rotation = "", backcolor = Color(150,255,150))
        }
    }
}

@Composable
fun Twoplayers(navController: NavController, life: Int) {
    var p1lives: Int by rememberSaveable { mutableIntStateOf(life) }
    var p2lives: Int by rememberSaveable { mutableIntStateOf(life) }

    BackgroundImage()
    ConstraintLayout (Modifier.fillMaxSize()){
        var (p1,p2) = createRefs()
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .constrainAs(p1) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }){
            LifePanel(text = "$p1lives", rotation = "topdown", backcolor = Color(150,150,255))
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .constrainAs(p2) {
                top.linkTo(p1.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }){
            LifePanel(text = "$p2lives", rotation = "default", backcolor = Color(255,150,150))
        }
    }
}
