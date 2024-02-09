package com.pixelperfectsoft.tcg_nexus.cards

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.model.Card
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.model.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.DataState
import androidx.compose.ui.window.Dialog as Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

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
                number = totalcards.value.toString(),
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

    }
}

@Composable
fun ShowLazyList(cards: List<Card>) {
    val currentSelectedItem = remember{mutableStateOf(cards[0])}
    val show = rememberSaveable { mutableStateOf(false) } //Variable booleana de estado para mostrar u ocultar el dialogo de informacion de cada carta
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(cards){
            Log.d("Cards", "Loading card ${it.name}")
            CardItem(card = it, show = show, currentSelectedItem = currentSelectedItem)
        }
        item { 
            Button(onClick = { }) {
                Text(text = "+")
            }
        }
    } )
    /*LazyColumn(Modifier.fillMaxSize()) {//Columna que solo renderiza los elementos visibles
        items(cards) {
            Log.d("Cards", "Loading card ${it.name}")
            CardItem(card = it, show = show)
        }
    }*/
}

@Composable
fun FilterButton() {
    val show = rememberSaveable { mutableStateOf(false) }
    MyButton(
        text = "Filtrar",
        onclick = { show.value = true },
        containercolor = Color.White,
        bordercolor = Color.Black,
        textcolor = Color.Black
    )
    FilterDialog(show)
}

@Composable
fun FilterDialog(show: MutableState<Boolean>) {
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {

        }
    }

}

@Composable
fun CardDialog(show: MutableState<Boolean>, card: Card) {
    Dialog(onDismissRequest = { show.value = false }) {
        Row(
            Modifier
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(16.dp))
                CardImage(card = card, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = card.name.toString(), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = card.type_line.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = card.oracle_text.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = card.rarity.toString(), style = TextStyle())
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    if (card.prices_eur != "") {
                        Text(text = "${card.prices_eur} EUR")
                    } else {
                        Text(text = "??? EUR")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    if (card.prices_eur_foil != "") {
                        Text(text = "Foil -> ${card.prices_eur_foil} EUR")
                    } else {
                        Text(text = "Foil -> ??? EUR")
                    }
                }
                Row {
                    if (card.prices_eur != "") {
                        Text(text = "${card.prices_usd} USD")
                    } else {
                        Text(text = "??? USD")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    if (card.prices_usd_foil != "") {
                        Text(text = "Foil -> ${card.prices_usd_foil} USD")
                    } else {
                        Text(text = "Foil -> ??? USD")
                    }
                }
            }
        }
    }
}

@Composable
fun CardItem(
    card: Card,
    show: MutableState<Boolean>,
    currentSelectedItem: MutableState<Card>,
) {
    Box(modifier = Modifier
        .background(Color.Transparent)
        .clickable { currentSelectedItem.value = card; show.value = true }){
        Column {
            Box(modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .background(Color.Green)){
                AsyncImage(
                    model = card.image_uris_normal.toString().replace("normal","large"),
                    contentDescription = card.name.toString(),
                )
                //CardImage(card = card, modifier = Modifier.fillMaxSize())
            }

            Column(modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = card.name.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    //modifier = Modifier.padding(start = 16.dp)
                )
                //Spacer(modifier = Modifier.height(30.dp))
                /*Text(
                    text = card.type_line.toString(),
                    modifier = Modifier.padding(start = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                )*/
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
    if (show.value) {
        CardDialog(show = show, card = currentSelectedItem.value)
    }
}

@Composable
fun CardImage(card: Card, modifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(model = card.image_uris_normal),
        //painter = rememberAsyncImagePainter(model = "https://images.pexels.com/photos/268533/pexels-photo-268533.jpeg"),
        contentDescription = card.name.toString(),
        modifier = modifier
    )
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

fun createGradientBrush(colors: List<Color>, isVertical: Boolean = true): Brush {
    val endOffset = if (isVertical) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset,
        tileMode = TileMode.Clamp
    )
}

@Composable
fun SetData(viewModel: CardViewModel, totalcards: MutableIntState) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Cargando lista de cartas...")
                }
            }
        }
        is DataState.Success -> {
            totalcards.intValue = result.data.size
            ShowLazyList(cards = result.data)
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
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error Loading Cards")
            }
        }
    }
}