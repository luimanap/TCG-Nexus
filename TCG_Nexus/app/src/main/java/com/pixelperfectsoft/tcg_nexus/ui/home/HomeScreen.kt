package com.pixelperfectsoft.tcg_nexus.ui.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.classes.News
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreen() {
    val cards = CardViewModel(context = LocalContext.current)
    val news = listOf(
        /*News(
            image = R.drawable.ic_launcher_background,
            title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
            link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler",
            provider = "MTGGOLDFISH",
            date = "Jan 17, 2024"
        ),*/
        News(
            image = R.drawable.ic_launcher_background,
            title = "Ravnica: Murders at Karlov Manor Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Ravnica+Murders+at+Karlov+Manor#paper",
            provider = "MTGGOLDFISH",
            date = "Feb 9, 2024"

        ),
        News(
            image = R.drawable.ic_launcher_background,
            title = "Fallout Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Fallout#paper",
            provider = "MTGGOLDFISH",
            date = "Mar 8, 2024"
        ),
        News(
            image = R.drawable.ic_launcher_background,
            title = "Outlaws of Thunder Junction Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Outlaws+of+Thunder+Junction#paper",
            provider = "MTGGOLDFISH",
            date = "Apr 12, 2024"
        ),
        News(
            image = R.drawable.ic_launcher_background,
            title = "Assassin's Creed Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Assassins+Creed#paper",
            provider = "MTGGOLDFISH",
            date = "Jul 5, 2024"
        ),
        News(
            image = R.drawable.ic_launcher_background,
            title = "Bloomburrow Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Bloomburrow#paper",
            provider = "MTGGOLDFISH",
            date = "Aug 2, 2024"
        ),

        )
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(createGradientBrush(colors = colors))
    ) {
        Spacer(Modifier.fillMaxHeight(0.05f))
        MyLogo(height = 250)
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        Text(text = "INICIO", fontSize = 40.sp)
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Text(text = "ULTIMAS NOTICIAS")
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            items(news) {
                NewsPanel(news = it)
                if (news.indexOf(it) != news.size) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 18.dp))
                }
            }
        }/*Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 4.dp)
        ) {
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 18.dp))
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 18.dp))
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 18.dp))
            NewsPanel(
                image = R.drawable.ic_launcher_background,
                title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
                link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler"
            )
        }*/
    }
}

@Composable
fun NewsPanel(news: News) {
    val uriHandler = LocalUriHandler.current
    Column(modifier = Modifier.padding(8.dp)) {
        Row {
            Text(text = news.provider, fontWeight = FontWeight.Light)
        }
        Row(modifier = Modifier
            .fillMaxWidth()

            .clickable {
                uriHandler.openUri(news.link)
            }) {
            Box(Modifier.padding(vertical = 16.dp)) {
                Image(painter = painterResource(id = news.image), contentDescription = news.title)
            }
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {

                Text(text = news.title, textAlign = TextAlign.Justify)
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Text(text = news.date, fontWeight = FontWeight.ExtraLight, fontStyle = FontStyle.Italic)
                }
            }
        }
    }

}