package com.pixelperfectsoft.tcg_nexus.ui.cards

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CollectionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FilterButton(filtershow: MutableState<Boolean>) {
    SmallFloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp),
        onClick = { filtershow.value = true },
        shape = CircleShape,
        content = {
            Icon(
                painter = rememberAsyncImagePainter(model = R.drawable.materialsymbolsfilteralt),
                contentDescription = ""
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
    )
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalSheet(
    filtershow: MutableState<Boolean>,
    sheetState: SheetState,
    scope: CoroutineScope,
    cardviewmodel: CardViewModel?,
    colviewmodel: CollectionViewModel?,
    screen: String
) {
    val context = LocalContext.current
    var searchinput by rememberSaveable { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = { filtershow.value = false },
        sheetState = sheetState,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = searchinput,
                onValueChange = { searchinput = it },
                label = { Text(text = "Buscar...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
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
                            when(screen){
                                "cards" -> cardviewmodel?.searchCardsByName(searchinput)
                                "col" -> colviewmodel?.searchCardsByName(searchinput)
                            }
                        } else {
                            when(screen){
                                "cards" -> cardviewmodel?.resetSearch()
                                "col" -> colviewmodel?.resetSearch()
                            }
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                    }
                }
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = "Ordenar por...")
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.orderBy("name")
                            "col" -> colviewmodel?.orderBy("name")
                        }

                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Ordering by name...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "Nombre")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.orderBy("cmc")
                            "col" -> colviewmodel?.orderBy("cmc")
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Ordering by CMC...", Toast.LENGTH_SHORT).show()
                    }) {
                    Text(text = "CMC")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.orderBy("color")
                            "col" -> colviewmodel?.orderBy("color")
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Ordering by Color...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "Color")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.orderBy("type")
                            "col" -> colviewmodel?.orderBy("type")
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Ordering by Type...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "Tipo")
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = "Mostrar...")
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.setLimit(100)
                            "col" -> colviewmodel?.setLimit(100)
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Mostrando 100 cartas...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "100")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.setLimit(1000)
                            "col" -> colviewmodel?.setLimit(1000)
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Mostrando 1000 cartas...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "1000")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.setLimit(10000)
                            "col" -> colviewmodel?.setLimit(10000)
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Mostrando 10000 cartas...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "10000")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        when(screen){
                            "cards" -> cardviewmodel?.setLimit(-1)
                            "col" -> colviewmodel?.setLimit(-1)
                        }
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                filtershow.value = false
                            }
                        }
                        Toast.makeText(context, "Mostrando todas las cartas...", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                    Text(text = "Todas")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddModalSheet(addshow: MutableState<Boolean>, sheetState: SheetState, scope: CoroutineScope) {
    ModalBottomSheet(
        onDismissRequest = { addshow.value = false },
        sheetState = sheetState
    ) {
        Text(text = "Add Cards")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddButton(addshow: MutableState<Boolean>) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        onClick = { addshow.value = true },
        shape = CircleShape,
        content = { Icon(imageVector = Icons.Default.Add, contentDescription = "") },
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
    )
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
