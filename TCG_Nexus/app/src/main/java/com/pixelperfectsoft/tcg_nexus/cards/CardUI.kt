package com.pixelperfectsoft.tcg_nexus.cards

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.model.Card
import kotlinx.coroutines.launch
import java.util.Locale

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDialog(show: MutableState<Boolean>, card: Card) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    if (show.value) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch { sheetState.hide() }
                show.value = false
            },
            sheetState = sheetState,
            content = {
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = card.name.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(thickness = 1.5.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                    CardImage(
                        card = card, modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Row {
                        Text(text = card.type_line.toString().replace("�", "-"))
                        Spacer(modifier = Modifier.fillMaxWidth(0.7f))
                        Text(
                            text = card.rarity.toString().uppercase(Locale.ROOT),
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = card.oracle_text.toString().replace("�", "-"),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (card.prices_eur != "") {
                            Text(text = "${card.prices_eur.toString().toDouble() / 100} EUR")
                        } else {
                            Text(text = "??? EUR")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (card.prices_eur_foil != "") {
                            Text(
                                text = "Foil -> ${
                                    card.prices_eur_foil.toString().toDouble() / 100
                                } EUR"
                            )
                        } else {
                            Text(text = "Foil -> ??? EUR")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (card.prices_eur != "") {
                            Text(text = "${card.prices_usd.toString().toDouble() / 100} USD")
                        } else {
                            Text(text = "??? USD")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        if (card.prices_usd_foil != "") {
                            Text(
                                text = "Foil -> ${
                                    card.prices_usd_foil.toString().toDouble() / 100
                                } USD"
                            )
                            Log.d("price", "${card.prices_usd_foil.toString().toDouble()}")
                        } else {
                            Text(text = "Foil -> ??? USD")
                        }
                    }
                }
            }
        )
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
        .clickable { currentSelectedItem.value = card; show.value = true }) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
                    .background(Color.Green)
            ) {
                AsyncImage(
                    model = card.image_uris_normal.toString().replace("normal", "large"),
                    contentDescription = card.name.toString()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = card.name.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
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
    CardDialog(show = show, card = currentSelectedItem.value)

}

@Composable
fun CardImage(card: Card, modifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(model = card.image_uris_normal),
        contentDescription = card.name.toString(),
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}