package com.pixelperfectsoft.tcg_nexus.ui.cards

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pixelperfectsoft.tcg_nexus.model.classes.Card
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CollectionViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.DataState
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.InfoCard
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun Collection(navController: NavHostController, viewModel: CollectionViewModel = viewModel()) {
    val backcolors = listOf(
        Color.Transparent,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background
    )
    val totalcards = rememberSaveable { mutableIntStateOf(0) }
    BackgroundImage()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors))
    ) {
        //Collection info
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            InfoCard(
                text = "Cards in collection",
                number = totalcards.intValue.toString(),
                containercolor = MaterialTheme.colorScheme.primary,
                contentcolor = Color.White,
                contenttype = "number"
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        //Lista de cartas
        LoadCollection(
            navController = navController,
            viewModel = viewModel,
            totalcards = totalcards
        )
    }
}

@Composable
fun LoadCollection(
    navController: NavHostController,
    viewModel: CollectionViewModel,
    totalcards: MutableIntState,
) {
    when (val result = viewModel.state.value) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Loading your collection...")
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator()
                }
            }
        }

        is DataState.Success -> {
            totalcards.intValue = result.data.size
            Log.d("DataState success", "Datastate success")
            ShowCollectionLazyList(
                navController = navController,
                viewModel = viewModel,
                cards = result.data
            )
        }

        is DataState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = result.message)
            }
        }

        is DataState.Empty -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Collection is empty")
            }
        }
    }
}

@Composable
fun ShowCollectionLazyList(
    navController: NavHostController,
    cards: MutableList<Card>,
    viewModel: CollectionViewModel,
) {
    val currentSelectedItem = remember { mutableStateOf(Card()) }

    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(3),
            content = {
                items(cards) {
                    CardItem(
                        navController = navController,
                        card = it,
                        currentSelectedItem = currentSelectedItem,
                        dialogplace = "collection",
                    )
                }
            })
        Column(horizontalAlignment = Alignment.End) {
            FilterButton("col", null, viewModel)
        }
    }
}
