package com.pixelperfectsoft.tcg_nexus.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush
import kotlinx.coroutines.launch

@Composable
fun Decks(navController: NavController) {
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    val totalcards by rememberSaveable { mutableIntStateOf(0) }
    val colorsused by rememberSaveable { mutableStateOf("BG") }
    BackgroundImage()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors))
    ) {

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        //Collection info
        Row(modifier = Modifier.fillMaxHeight(0.15f)) {
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            InfoCard(
                text = "Mazos creados",
                number = totalcards.toString(),
                containercolor = Color(92, 115, 255),
                contentcolor = Color.White,
                contenttype = "number"
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            InfoCard(
                text = "Colores más usados",
                number = colorsused,
                containercolor = Color.White,
                contentcolor = Color.Black,
                contenttype = "text"
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        //Card list
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .verticalScroll(rememberScrollState())
        ) {

        }
        AddButton()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddButton() {
    val show = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { show.value = true },
                shape = CircleShape,
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create deck"
                    )
                },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                )
            )
        },
        containerColor = Color.Transparent
    ) { it -> }
    if (show.value) {
        ModalBottomSheet(
            onDismissRequest = { show.value = false },
            sheetState = sheetState
        ) {
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        show.value = false
                    }
                }
            }) {

            }
        }
    }
}
