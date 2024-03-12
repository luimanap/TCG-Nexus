package com.pixelperfectsoft.tcg_nexus.cards

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.Card
import com.pixelperfectsoft.tcg_nexus.model.CollectionViewModel
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
    val context = LocalContext.current
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDialog(
    show: MutableState<Boolean>,
    card: Card,
    collectionViewModel: CollectionViewModel = CollectionViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current
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
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                    CardImage(
                        card = card, modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .fillMaxWidth()
                    )
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                    )
                    Text(text = "─ ${card.type_line.toString().replace("�", "-")} ─")
                    Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                    Text(
                        text = "Rareza : ${card.rarity.toString().uppercase()} ",
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = card.oracle_text.toString().replace("�", "-"),
                        textAlign = TextAlign.Justify
                    )
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Text(text = "Precios de compra estimados: ")
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
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Column {
                        MyButton(
                            text = "Añadir a la colección",
                            onclick = {
                                var collection = collectionViewModel.collection.value.cards
                                collection.add(card)
                                collectionViewModel.updateCollection(collection)
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        show.value = false
                                    }
                                }
                            },
                            containercolor = MaterialTheme.colorScheme.primary,
                            bordercolor = MaterialTheme.colorScheme.primary,
                            textcolor = Color.White
                        )
                        MyButton(
                            text = "Comprar en Cardmarket",
                            onclick = { uriHandler.openUri(card.purchase_uris_cardmarket.toString()) },
                            containercolor = MaterialTheme.colorScheme.primary,
                            bordercolor = MaterialTheme.colorScheme.primary,
                            textcolor = Color.White
                        )
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
            //.background(Color.Green)
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                if (card.image_uris_normal.toString() != "") {
                    AsyncImage(
                        model = card.image_uris_normal.toString().replace("normal", "small"),
                        contentDescription = card.name.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = R.drawable.card_back_unavailable,
                        contentDescription = card.name.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text = card.name.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                //.padding(horizontal = 25.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
        painter = rememberAsyncImagePainter(
            model = card.image_uris_normal.toString().replace("normal", "large")
        ),
        contentDescription = card.name.toString(),
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}