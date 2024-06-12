package com.pixelperfectsoft.tcg_nexus.ui.cards

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CollectionViewModel
import com.pixelperfectsoft.tcg_nexus.ui.MyButton
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterButton(
    screen: String,
    cardviewmodel: CardViewModel?,
    colviewmodel: CollectionViewModel?,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = { scope.launch { sheetState.show() } },
        shape = CircleShape,
        content = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = ""
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
    )

    if (sheetState.isVisible) {
        FilterModalSheet(
            sheetState = sheetState,
            scope = scope,
            cardviewmodel = cardviewmodel,
            colviewmodel = colviewmodel,
            screen = screen
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDialog(
    navController: NavHostController,
    sheetState: SheetState,
    card: Card,
    collectionViewModel: CollectionViewModel = CollectionViewModel(),
    dialogplace: String,
) {
    if (sheetState.isVisible) {
        val scope = rememberCoroutineScope()
        val uriHandler = LocalUriHandler.current

        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
            },
            sheetState = sheetState,
            content = {
                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = card.name.toString(),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 28.sp,
                            )
                    )
                    Text(
                        text = card.type_line.toString().replace("�", "-"),
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(
                            id = when (card.rarity.toString().lowercase()) {
                                "common" -> R.drawable.rarity_common
                                "uncommon" -> R.drawable.rarity_uncommon
                                "rare" -> R.drawable.rarity_rare
                                "mythic" -> R.drawable.rarity_mythic
                                else -> R.drawable.rarity_common
                            }
                        ),
                        contentDescription = "rarity",
                        modifier = Modifier
                            .height(4.dp)
                            .width(162.dp),
                        contentScale = ContentScale.Crop
                    )
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                    CardImage(
                        card = card, modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .fillMaxWidth()
                            .rotate(
                                if (card.type_line
                                        .toString()
                                        .lowercase()
                                        .contains("plane") && !card.type_line
                                        .toString()
                                        .lowercase()
                                        .contains("swalker")
                                ) {
                                    90f
                                } else {
                                    0f
                                }
                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = card.set_name.toString().replace("�", "-"),
                        fontWeight = FontWeight.SemiBold
                    )
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    if (card.type_line.toString().lowercase().contains("planeswalker")) {
                        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 120.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Lealtad", fontWeight = FontWeight.Bold, style = TextStyle(
                                    fontSize = 20.sp
                                ), modifier = Modifier.padding(top = 8.dp)
                            )
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.loyalty),
                                    contentDescription = "loyalty",
                                    modifier = Modifier.size(50.dp)
                                )
                                Text(
                                    text = "${card.loyalty}",
                                    color = Color.White,
                                    style = TextStyle(fontSize = 20.sp)
                                )
                            }
                        }
                    }
                    if (card.type_line.toString().lowercase().contains("creature")) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 120.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sword),
                                    contentDescription = "power",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(text = "${card.power}")
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.heart),
                                    contentDescription = "toughness",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(text = "${card.toughness}")
                            }
                        }
                        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = card.oracle_text.toString().replace("�", "-")
                            .replace("?", "-"),
                        textAlign = TextAlign.Justify
                    )
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = "Estimated price", fontWeight = FontWeight.Bold, style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (card.prices_eur != "") {
                            Text(
                                text = "${
                                    card.prices_eur.toString().toDouble() / 100
                                } €"
                            )
                        } else {
                            Text(text = "??? €")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            if (card.prices_eur_foil != "") {
                                Text(
                                    text = "${
                                        card.prices_eur_foil.toString().toDouble() / 100
                                    } €",
                                    color = Color(237, 138, 25),
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                Text(
                                    text = "??? €", color = Color(237, 138, 25),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.5.dp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Column {
                        if (dialogplace == "collection") {

                            MyButton(
                                text = "Delete",
                                onclick = {
                                    collectionViewModel.deleteCardFromCollection(card)
                                    scope.launch { sheetState.hide() }
                                    navController.navigate(MyScreenRoutes.UPDATE)
                                },
                                containercolor = MaterialTheme.colorScheme.primary,
                                bordercolor = MaterialTheme.colorScheme.primary,
                                textcolor = Color.White
                            )
                        } else if (dialogplace == "allcards") {
                            MyButton(
                                text = "Add to collection",
                                onclick = {
                                    val collection = collectionViewModel.collection.value.cards
                                    collection.add(card)
                                    collectionViewModel.updateCollection(collection)
                                    scope.launch { sheetState.hide() }
                                },
                                containercolor = MaterialTheme.colorScheme.primary,
                                bordercolor = MaterialTheme.colorScheme.primary,
                                textcolor = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { uriHandler.openUri(card.purchase_uris_cardmarket.toString()) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp, end = 30.dp)
                                .border(
                                    5.dp,
                                    MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ),
                            colors = ButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            )
                        ) {
                            Text(text = "Buy in CardMarket")
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    navController: NavHostController,
    card: Card,
    currentSelectedItem: MutableState<Card>,
    dialogplace: String,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Box(modifier = Modifier
        .background(Color.Transparent)
        .clickable {
            scope
                .launch { sheetState.show() }
                .invokeOnCompletion {
                    if (sheetState.hasPartiallyExpandedState) {
                        scope.launch { sheetState.expand() }
                    }
                }
            currentSelectedItem.value = card
        }) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                if (card.image_uris_normal.toString() != "") {
                    AsyncImage(
                        model = card.image_uris_normal.toString().replace("normal", "small"),
                        contentDescription = card.name.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.card_back_small)
                    )
                } else {
                    AsyncImage(
                        model = R.drawable.card_back_unavailable,
                        contentDescription = card.name.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.card_back_small)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = card.name.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
    CardDialog(
        navController = navController,
        sheetState = sheetState,
        card = currentSelectedItem.value,
        dialogplace = dialogplace
    )

}


@Composable
fun CardImage(card: Card, modifier: Modifier) {
    AsyncImage(
        model = card.image_uris_normal.toString().replace("normal", "large"),
        contentDescription = card.name.toString(),
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    cardviewmodel: CardViewModel?,
    colviewmodel: CollectionViewModel?,
    screen: String,
) {
    val context = LocalContext.current
    val searchinput = rememberSaveable { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.navigationBarsPadding(),
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = searchinput.value,
                    onValueChange = { searchinput.value = it },
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
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.DarkGray
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    if (searchinput.value != "") {
                        Log.d("search", "Searching by name -> ${searchinput.value}")
                        when (screen) {
                            "cards" -> cardviewmodel?.searchCardsByName(searchinput.value)
                            "col" -> colviewmodel?.searchCardsByName(searchinput.value)
                        }
                    } else {
                        when (screen) {
                            "cards" -> cardviewmodel?.resetSearch()
                            "col" -> colviewmodel?.resetSearch()
                        }
                    }
                    scope.launch { sheetState.hide() }
                }) {
                    Text(text = "Search")
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        })
}