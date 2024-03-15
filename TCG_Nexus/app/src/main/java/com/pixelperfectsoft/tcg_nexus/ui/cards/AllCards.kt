package com.pixelperfectsoft.tcg_nexus.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.InfoCard
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun AllCards(navController: NavController, viewModel: CardViewModel = viewModel()){
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    val totalcards = rememberSaveable { mutableIntStateOf(0) }
    val estimatedCost = rememberSaveable { mutableFloatStateOf(0f) }
    BackgroundImage()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors))
    ) {
        //Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        //Informacion
        Column(modifier = Modifier.padding(horizontal = 16.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            /*InfoCard(
                text = "BUSCAR CARTAS",
                number = "",
                containercolor = Color.White,
                contentcolor = Color.Black,
                contenttype = "number"
            )*/
            Spacer(modifier = Modifier.fillMaxHeight(0.025f))
            InfoCard(
                text = "Cartas cargadas",
                number = totalcards.intValue.toString(),
                containercolor = MaterialTheme.colorScheme.primary,
                contentcolor = Color.White,
                contenttype = "number"
            )

        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        //Boton de filtrar
        //FilterButton()

        //Lista de cartas
        SetData(viewModel = viewModel, totalcards, estimatedCost)


    }
}