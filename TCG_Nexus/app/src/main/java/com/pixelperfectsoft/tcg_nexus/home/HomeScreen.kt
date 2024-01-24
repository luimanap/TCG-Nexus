package com.pixelperfectsoft.tcg_nexus.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.MyLogo
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.collection.createGradientBrush

@Composable
fun HomeScreen(navController: NavHostController) {
    val colors = listOf(
        Color.Transparent,
        Color(255, 255, 255, 100),
        Color(255, 255, 255, 150),
        Color(255, 255, 255, 200),
        Color.White,
        Color.White,
        Color.White,
        Color.White,
        Color.White
    )
    BackgroundImage()
    Column(
        Modifier
            .fillMaxSize()
            .background(createGradientBrush(colors = colors))
    ) {
        Spacer(Modifier.fillMaxHeight(0.05f))
        MyLogo(height = 250)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "ULTIMAS NOTICIAS")
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
        }
    }
}

@Composable
fun NewsPanel(image: Int, title: String, link: String) {
    Row {
        Box(Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = image), contentDescription = title)
        }
        Column(Modifier.padding(16.dp)) {
            Text(text = title)
        }
    }
}

