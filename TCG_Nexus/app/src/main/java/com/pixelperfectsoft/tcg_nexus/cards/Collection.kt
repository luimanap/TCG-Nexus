package com.pixelperfectsoft.tcg_nexus.cards

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.model.Card
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.model.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.DataState
import androidx.compose.ui.window.Dialog as Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter

@Composable
fun Collection(navController: NavController, viewModel: CardViewModel = viewModel()) {
    //val blacklotusimg = "https://cards.scryfall.io/normal/front/0/9/0948e6dc-8af7-45d3-91de-a2aebee83e82.jpg?1559591784"
    //val cards = listOf(
    /*Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    ),
    Card(
        name = "Black Lotus",
        description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ",
        type = "Mono Artifact",
        rarity = "Rare",
        price = 3500f,
        image = blacklotusimg
    )
    )*/
    val backcolors = listOf(
        Color.Transparent,
        Color(230, 230, 230),
        Color(225, 225, 225),
        Color(225, 225, 225),
        Color(225, 225, 225)
    )
    val totalcards by rememberSaveable { mutableIntStateOf(0) }
    var estimatedCost by rememberSaveable { mutableFloatStateOf(0f) }
    /*for (i in cards) {
        estimatedCost += i.get_price()
    }*/
    val estimatedcostString by rememberSaveable { mutableStateOf("$estimatedCost €") }
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
                number = totalcards.toString(),
                containercolor = Color(92, 115, 255),
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

        //Barra de busqueda
        SearchButton()

        //Lista de cartas
        SetData(viewModel = viewModel)

    }
}

@Composable
fun ShowLazyList(cards: List<Card>) {
    val show =
        rememberSaveable { mutableStateOf(false) } //Variable booleana de estado para mostrar u ocultar el dialogo de informacion de cada carta
    LazyColumn(Modifier.fillMaxSize()) {//Columna que solo renderiza los elementos visibles
        items(cards) {
            Log.d("Cards", "Loading card ${it.name}")
            CardItem(card = it, show = show)

        }
    }
}

@Composable
fun SearchButton() {
    val show = rememberSaveable { mutableStateOf(false) }
    MyButton(
        text = "Search",
        onclick = { show.value = true },
        containercolor = Color.White,
        bordercolor = Color.Black,
        textcolor = Color.Black
    )
    SearchDialog(show)
}

@Composable
fun SearchDialog(show: MutableState<Boolean>) {
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
            Column(Modifier.fillMaxWidth(0.5f)) {
                Text(text = card.name.toString(), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = card.type_line.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = card.oracle_text.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = card.rarity.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${card.prices_eur} €")
            }
            Image(
                painter = rememberAsyncImagePainter(card.image_uris_normal.toString().trim()),
                contentDescription = card.name.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            )
        }
    }
}

@Composable
fun CardItem(
    card: Card,
    show: MutableState<Boolean>,
) {
    OutlinedButton(
        onClick = {
            show.value = true
        },
        border = BorderStroke(0.dp, Color.Transparent),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color.Transparent
        ),
        shape = RectangleShape
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(card.image_uris_normal.toString().trim()),
                contentDescription = card.name.toString(),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(16.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = card.name.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = card.type_line.toString(),
                    modifier = Modifier.padding(start = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = card.oracle_text.toString(),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }
    }
    if (show.value) {
        CardDialog(show = show, card = card)
    }
}

@Composable
fun InfoCard(
    text: String,
    number: String,
    containercolor: Color,
    contentcolor: Color,
    contenttype: String
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
fun SetData(viewModel: CardViewModel) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Loading card list...")
                }
            }
        }

        is DataState.Success -> {
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