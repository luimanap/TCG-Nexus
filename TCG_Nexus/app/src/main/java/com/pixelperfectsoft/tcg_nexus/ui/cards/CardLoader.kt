package com.pixelperfectsoft.tcg_nexus.ui.cards

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.DataState
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowLazyList(cards: List<Card>, viewModel: CardViewModel, navController: NavController) {
    val currentSelectedItem = remember { mutableStateOf(Card()) }
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(3),
            content = {
                /*item {
                    Box(Modifier.size(8.dp).background(Color.Transparent)) {}
                }
                item {
                    PageButtons(viewModel, LocalContext.current)
                }
                item {
                    Box(Modifier.size(8.dp).background(Color.Transparent)) {}
                }*/
                items(cards) {
                    Log.d("Cards", "Loading card ${it.name}")
                    Log.d("card_image", it.image_uris_normal.toString())
                    CardItem(
                        card = it,
                        currentSelectedItem = currentSelectedItem,
                        dialogplace = "allcards"
                    )
                }
                item {
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(Color.Transparent)) {}
                }
                item {
                    PageButtons(viewModel, LocalContext.current)
                }
                item {
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(Color.Transparent)) {}
                }
            })
        Column(horizontalAlignment = Alignment.End) {
            FilterButton("cards", cardviewmodel = viewModel, colviewmodel = null)
        }
    }
}

@Composable
fun PageButtons(viewModel: CardViewModel, context: Context) {
    Column {
        Text(text = "Mostrando ${viewModel.start+1}..${viewModel.end} cartas", style = TextStyle(
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        ))
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                viewModel.prevPage(context)
            }) {
                Text(text = "<")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                viewModel.nextPage(context)
            }) {
                Text(text = ">")
            }
        }
    }
    
}

@Composable
fun SetData(
    navController: NavController,
    viewModel: CardViewModel,
    //totalcards: MutableIntState,
    //estimatedCost: MutableFloatState
) {

    viewModel.load(LocalContext.current)
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    //CircularProgressIndicator()
                    Text(text = "Revelando los secretos de lo arcano...")
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator()
                }
            }
        }

        is DataState.Success -> {
            //totalcards.intValue = result.data.size
            /*price@ for (i in result.data){
                if(i.prices_eur!=""){
                    estimatedCost.floatValue += (i.prices_eur.toString().toFloat())/100
                }
            }*/
            ShowLazyList(viewModel = viewModel, cards = result.data, navController = navController)
        }

        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = result.message)
            }
        }

        is DataState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No se han encontrado cartas")
            }
        }
    }
}