package com.pixelperfectsoft.tcg_nexus.ui.cards

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
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
import androidx.navigation.NavHostController
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.DataState


@Composable
fun ShowLazyList(cards: List<Card>, viewModel: CardViewModel, navController: NavHostController) {
    val currentSelectedItem = remember { mutableStateOf(Card()) }
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(3),
            content = {
                val extraitems = if (cards.size % 3 == 0) {
                    0
                } else {
                    3 - (cards.size % 3)
                }
                items(cards) {
                    CardItem(
                        navController = navController,
                        card = it,
                        currentSelectedItem = currentSelectedItem,
                        dialogplace = "allcards"
                    )
                }
                items(extraitems) {
                    Box(modifier = Modifier.size(100.dp, 100.dp))
                }
                item {
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(Color.Transparent)
                    )
                }
                item {
                    PageButtons(viewModel, LocalContext.current)
                }
                item {
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(Color.Transparent)
                    )
                }
            })
        Column(horizontalAlignment = Alignment.End) {
            FilterButton("cards", cardviewmodel = viewModel, colviewmodel = null)
        }
    }
}

@Composable
fun PageButtons(viewModel: CardViewModel, context: Context) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (viewModel.start == 0) {
                "Showing ${viewModel.start + 1}-${viewModel.end} of ${viewModel.tempList.size}"
            } else {
                "Showing ${viewModel.start}-${viewModel.end} of ${viewModel.tempList.size}"
            }, style = TextStyle(
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                viewModel.prevPage()
            }) {
                Text(text = "<")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                viewModel.nextPage()
            }) {
                Text(text = ">")
            }
        }
    }

}

@Composable
fun SetData(
    navController: NavHostController,
    viewModel: CardViewModel,
) {
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