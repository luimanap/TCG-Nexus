package com.example.tcg_nexus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun PlayScreen(navController: NavController) {
    BackgroundImage()
    var players by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(204, 204, 204, 90)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectPlayers(navController)
    }
}

@Composable
fun Fourplayers(navController: NavController) {
    var p1lives: Int by rememberSaveable { mutableStateOf(40) }
    var p2lives: Int by rememberSaveable { mutableStateOf(40) }
    var p3lives: Int by rememberSaveable { mutableStateOf(40) }
    var p4lives: Int by rememberSaveable { mutableStateOf(40) }

    BackgroundImage()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(204, 204, 204, 90)),
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Spacer(modifier = Modifier.size(4.dp))
            LifePanel(text = "${p1lives}", rotation = "left")
            Spacer(modifier = Modifier.size(8.dp))
            LifePanel(text = "${p2lives}", rotation = "right")
            Spacer(modifier = Modifier.size(4.dp))
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Spacer(modifier = Modifier.size(4.dp))
            LifePanel(text = "${p3lives}", rotation = "left")
            Spacer(modifier = Modifier.size(8.dp))
            LifePanel(text = "${p4lives}", rotation = "right")
            Spacer(modifier = Modifier.size(4.dp))
        }
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
fun LifePanel(text: String, rotation: String) {
    var rotate: Float = 0f
    if (rotation == "left") {
        rotate = 90f
    } else if (rotation == "right") {
        rotate = -90f
    }
    Card(
        Modifier
            .width(200.dp)
            .height(400.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .rotate(rotate)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(fontSize = 140.sp, textAlign = TextAlign.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPlayers(navController: NavController) {
    var defaultvalue by rememberSaveable { mutableStateOf("") }
    var isexpanded by rememberSaveable { mutableStateOf(false) }

    val elements = listOf<String>("2 Players", "3 Players", "4 Players", "5 Players")
    Column {
        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(value = defaultvalue,
                onValueChange = { defaultvalue = it },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .clickable { isexpanded = true }
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Black, disabledTextColor = Color.Black
                ),
                label = { Text(text = "") })
            DropdownMenu(
                expanded = isexpanded,
                onDismissRequest = { isexpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in elements) {
                    DropdownMenuItem(text = { Text(text = i) },
                        onClick = { isexpanded = false; defaultvalue = i })
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            MyButton(text = "A Jugar!!", onclick = {
                when (defaultvalue) {
                    //"2 Players" ->,
                    //"3 Players" ->,
                    "4 Players" -> {
                        navController.navigate("4p")
                    }
                    //"5 Players" ->,
                    //"6 Players" ->
                }
            }, containercolor = Color(92, 115, 255))
        }
    }
}