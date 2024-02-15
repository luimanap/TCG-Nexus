package com.pixelperfectsoft.tcg_nexus.cards

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.model.Card
import com.pixelperfectsoft.tcg_nexus.model.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.DataState
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush
import kotlinx.coroutines.launch

@Composable
fun Collection(navController: NavController, viewModel: CardViewModel = viewModel()) {
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    val totalcards = rememberSaveable { mutableIntStateOf(0) }
    val estimatedCost = rememberSaveable { mutableFloatStateOf(0f) }
    val estimatedcostString by rememberSaveable { mutableStateOf("${estimatedCost.floatValue} €") }
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
                text = "Cartas en posesión",
                number = totalcards.intValue.toString(),
                containercolor = MaterialTheme.colorScheme.primary,
                contentcolor = Color.White,
                contenttype = "number"
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            InfoCard(
                text = "Valor estimado",
                number = estimatedcostString,
                containercolor = Color.White,
                contentcolor = Color.Black,
                contenttype = "number"
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        //Boton de filtrar
        FilterButton()

        //Lista de cartas
        SetData(viewModel = viewModel, totalcards)

        //Boton de añadir carta


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowLazyList(cards: List<Card>) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val currentSelectedItem = remember { mutableStateOf(cards[0]) }
    val show =
        rememberSaveable { mutableStateOf(false) } //Variable booleana de estado para mostrar u ocultar el dialogo de informacion de cada carta
    val addshow =
        rememberSaveable { mutableStateOf(false) } //Variable booleana de estado para mostrar u ocultar el dialogo de añadir cartas a la coleccion
    val filtershow = rememberSaveable {mutableStateOf(false) } //Variable booleana de estado para mostrar u ocultar el dialogo de filtrar u ordenar la lista
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(2),
            content = {
                items(cards) {
                    Log.d("Cards", "Loading card ${it.name}")
                    CardItem(card = it, show = show, currentSelectedItem = currentSelectedItem)
                }
                item {
                    Button(onClick = { }) {
                        Text(text = "+")
                    }
                }
            })
        Column(horizontalAlignment = Alignment.End) {
            SmallFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { addshow.value = true },
                shape = CircleShape,
                content = {
                    Icon(
                        painter = rememberAsyncImagePainter(model = R.drawable.materialsymbolsfilteralt), contentDescription = ""
                    )
                },
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            )
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                onClick = { addshow.value = true },
                shape = CircleShape,
                content = { Icon(imageVector = Icons.Default.Add, contentDescription = "") },
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            )
        }
    }
    if (addshow.value) {
        ModalBottomSheet(
            onDismissRequest = { addshow.value = false },
            sheetState = sheetState
        ) {
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        addshow.value = false
                    }
                }
            }) {

            }
        }
    }

}


@Composable
fun InfoCard(
    text: String,
    number: String,
    containercolor: Color,
    contentcolor: Color,
    contenttype: String,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = containercolor,
            contentColor = contentcolor
        )

    ) {
        Column {
            if (contenttype == "number") {
                Text(
                    text = text, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
                Text(
                    text = number, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp)
                        .padding(start = 10.dp), fontSize = 30.sp
                )
            } else if (contenttype == "text") {
                Text(
                    text = text, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = number, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp), fontSize = 30.sp
                )
            }
        }
    }
}


@Composable
fun SetData(viewModel: CardViewModel, totalcards: MutableIntState) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    //CircularProgressIndicator()
                    Text(text = "Cargando lista de cartas...")
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator()
                }
            }
        }

        is DataState.Success -> {
            totalcards.intValue = result.data.size
            ShowLazyList(cards = result.data)

            //
        }

        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = result.message)
            }
        }

        is DataState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Database Empty")
            }
        }
    }
}