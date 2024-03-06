package com.pixelperfectsoft.tcg_nexus.cards

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.MyTextField
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.Card
import com.pixelperfectsoft.tcg_nexus.model.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.DataState
import kotlinx.coroutines.launch


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
    val filtershow =
        rememberSaveable { mutableStateOf(false) } //Variable booleana de estado para mostrar u ocultar el dialogo de filtrar u ordenar la lista
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(3),
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
    if (filtershow.value) {
        val context = LocalContext.current
        var searchinput by rememberSaveable { mutableStateOf("") }
        var error = false
        ModalBottomSheet(
            onDismissRequest = { filtershow.value = false },
            sheetState = sheetState,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Search...")
                TextField(
                    value = searchinput,
                    onValueChange = { searchinput = it },
                    label = { Text(text = "Introduce el nombre de la carta") },
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
                    )
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(text = "Order by...")
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = {
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
            }
        }
    }
}

@Composable
fun SetData(
    viewModel: CardViewModel,
    totalcards: MutableIntState,
    estimatedCost: MutableFloatState
) {
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
            /*price@ for (i in result.data){
                if(i.prices_eur!=""){
                    estimatedCost.floatValue += (i.prices_eur.toString().toFloat())/100
                }
            }*/
            ShowLazyList(cards = result.data)
        }

        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = result.message)
            }
        }

        is DataState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No cards found")
            }
        }
    }
}