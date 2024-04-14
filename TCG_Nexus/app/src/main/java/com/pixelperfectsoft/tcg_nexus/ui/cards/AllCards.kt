package com.pixelperfectsoft.tcg_nexus.ui.cards

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.InfoCard
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllCards(navController: NavController, viewModel: CardViewModel = CardViewModel(context = LocalContext.current)) {
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    val totalcards = rememberSaveable { mutableIntStateOf(0) }
    val estimatedCost = rememberSaveable { mutableFloatStateOf(0f) }
    var searchinput by rememberSaveable { mutableStateOf("") }
    val searched = rememberSaveable { mutableStateOf(true) }
    //var viewModel: CardViewModel = CardViewModel(context = LocalContext.current)
    BackgroundImage()
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors))
    ) {
        when (searched.value) {
            false -> {
                TextField(
                    value = searchinput,
                    onValueChange = { searchinput = it },
                    label = { Text(text = "Buscar...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(top = 64.dp),
                    shape = RoundedCornerShape(45.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        focusedSupportingTextColor = Color.Red,
                        unfocusedSupportingTextColor = Color.Red
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            if (searchinput != "") {
                                Log.d("search", "Searching by name -> $searchinput")

                                searched.value = true
                                viewModel.searchCardsByName(searchinput)
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                        }
                    }
                )

            }

            true -> {
                //Informacion
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                        text = "Cartas encontradas",
                        number = totalcards.intValue.toString(),
                        containercolor = MaterialTheme.colorScheme.primary,
                        contentcolor = Color.White,
                        contenttype = "number"
                    )

                }
                Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                //Cargar la busqueda
                SetData(viewModel = viewModel, totalcards, estimatedCost)
            }

        }

        //Boton de filtrar
        //FilterButton()
        //Lista de cartas


    }
}