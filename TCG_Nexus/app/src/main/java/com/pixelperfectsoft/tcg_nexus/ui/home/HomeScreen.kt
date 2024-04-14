package com.pixelperfectsoft.tcg_nexus.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.classes.News
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreen() {
    val news = listOf(
        /*News(
            image = R.drawable.ic_launcher_background,
            title = "Ravnica: Murders at Karlov Manor Spoilers — January 17 | Big Blood Artist and Small Creature Doubler",
            link = "https://www.mtggoldfish.com/articles/ravnica-murders-at-karlov-manor-spoilers-january-17-big-blood-artist-and-small-creature-doubler",
            provider = "MTGGOLDFISH",
            date = "Jan 17, 2024"
        ),*/
        News(
            image = "https://images.ctfassets.net/s5n2t79q9icq/5IpzwfzisqktbGso4ADudz/b82affc918ac3f6ccc6e2b0bccc125ec/SuzzZi1lLfhTt7T_1600x1080.jpg?q=70",
            title = "Ravnica: Murders at Karlov Manor Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Ravnica+Murders+at+Karlov+Manor#paper",
            provider = "MTGGOLDFISH",
            date = "Feb 9, 2024"

        ),
        News(
            image = "https://freakcorp.com/cdn/shop/articles/fallout-mtg.jpg?v=1697756215",
            title = "Universes Beyond: Fallout Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Fallout#paper",
            provider = "MTGGOLDFISH",
            date = "Mar 8, 2024"
        ),
        News(
            image = "https://media.wizards.com/2024/images/daily/1920x1080_OTJ_Key-Art.jpg",
            title = "Outlaws of Thunder Junction Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Outlaws+of+Thunder+Junction#paper",
            provider = "MTGGOLDFISH",
            date = "Apr 12, 2024"
        ),
        News(
            image = "https://tcgcheap.com/wp-content/uploads/MTG-Assassins-Creed-universe-beyond.webp",
            title = "Universes Beyond: Assassin's Creed Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Assassins+Creed#paper",
            provider = "MTGGOLDFISH",
            date = "Jul 5, 2024"
        ),
        News(
            image = "https://media.wizards.com/2024/images/daily/28c0611a3f.jpg",
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
        }
    }
}

@Composable
fun NewsPanel(news: News) {
    val uriHandler = LocalUriHandler.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        //.border(width = 1.dp, color = Color.Black)
        .clickable {
            uriHandler.openUri(news.link)
        }) {
        Image(
            painter = rememberAsyncImagePainter(model = news.image),
            contentScale = ContentScale.Crop,
            contentDescription = news.title,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(100.dp)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            Box(modifier = Modifier.fillMaxHeight(0.5f)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "${news.provider} --- ${news.date}", fontWeight = FontWeight.Light)
                    Text(text = news.title, modifier = Modifier.fillMaxHeight(0.25f))
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = news.date,
                    fontWeight = FontWeight.ExtraLight,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}