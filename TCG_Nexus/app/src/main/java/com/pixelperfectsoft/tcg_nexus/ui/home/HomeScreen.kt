package com.pixelperfectsoft.tcg_nexus.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
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
        News(
            id = 1,
            image = R.drawable.news_bloomburrow,
            title = "Bloomburrow Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Bloomburrow#paper",
            provider = "MTGGOLDFISH",
            date = "Aug 2, 2024"
        ),
        News(
            id = 2,
            image = R.drawable.news_assassinscreed,
            title = "Assassin's Creed Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Assassins+Creed#paper",
            provider = "MTGGOLDFISH",
            date = "Jul 5, 2024"
        ),
        News(
            id = 3,
            image = R.drawable.news_bebop,
            title = "Cowboy Bebop Collaboration Promo Cards",
            link = "https://magic.wizards.com/en/news/announcements/announcing-cowboy-bebop-collaboration-promo-cards",
            provider = "WIZARDS OF THE COAST",
            date = "Apr 22, 2024"
        ),
        News(
            id = 4,
            image = R.drawable.news_outlaws,
            title = "Outlaws of Thunder Junction Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Outlaws+of+Thunder+Junction#paper",
            provider = "MTGGOLDFISH",
            date = "Apr 12, 2024"
        ),
        News(
            id = 5,
            image = R.drawable.news_secretlairfallout,
            title = "Secret Lair Equinox Superdrop 2024",
            link = "https://www.mtggoldfish.com/spoilers/Outlaws+of+Thunder+Junction#paper",
            provider = "WIZARDS OF THE COAST",
            date = "Apr 5, 2024"
        ),
        News(
            id = 6,
            image = R.drawable.news_fallout,
            title = "Fallout Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Fallout#paper",
            provider = "MTGGOLDFISH",
            date = "Mar 8, 2024"
        ),
        News(
            id = 7,
            image = R.drawable.news_karlovmanor,
            title = "Murders at Karlov Manor Spoilers",
            link = "https://www.mtggoldfish.com/spoilers/Ravnica+Murders+at+Karlov+Manor#paper",
            provider = "MTGGOLDFISH",
            date = "Feb 9, 2024"
        ),
    )
    val colors = listOf(
        Color.Transparent,
        Color(255, 255, 255, 100),
        Color(255, 255, 255, 150),
        Color(255, 255, 255, 200),
        Color(255, 255, 255, 200),
        Color(255, 255, 255, 250),
        Color(255, 255, 255, 250),
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
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        val state = rememberLazyListState()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            items(news) {
                NewsPanel(news = it, state = state)
            }
        }
    }
}

@Composable
fun NewsPanel(news: News, state: LazyListState) {
    val uriHandler = LocalUriHandler.current
    /*val visible = remember { mutableStateOf(true) }

    val itemOffset =
        remember(news) { state.layoutInfo.visibleItemsInfo.firstOrNull() { it.key == news.id } }?.offset
            ?: 0
    LaunchedEffect(visible.value) {
        // Si el elemento está completamente fuera de la vista, lo marcamos como no visible
        if (itemOffset > state.layoutInfo.viewportEndOffset) {
            visible.value = false
        }
    }
    AnimatedVisibility(
        visible = visible.value,
        enter = fadeIn(animationSpec = TweenSpec(durationMillis = 500)),
        exit = fadeOut(animationSpec = TweenSpec(durationMillis = 500))
    ) {*/
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(250, 250, 250),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                uriHandler.openUri(news.link)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (image, provider, title, date) = createRefs()
            AsyncImage(
                model = news.image,
                contentScale = ContentScale.FillHeight,
                contentDescription = news.title,
                modifier = Modifier
                    //.padding(vertical = 16.dp)
                    .height(105.dp)
                    .width(135.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
            Box(modifier = Modifier.constrainAs(provider) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(image.end, 15.dp)
            }) {
                Text(
                    text = news.provider,
                    fontWeight = FontWeight.Light,
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    top.linkTo(provider.bottom)
                    start.linkTo(image.end, 15.dp)
                }) {
                Text(
                    text = news.title,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.width(175.dp)
                )
            }
            Box(modifier = Modifier.constrainAs(date) {
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
            }) {
                Text(
                    text = news.date,
                    fontWeight = FontWeight.ExtraLight,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    //   }
}