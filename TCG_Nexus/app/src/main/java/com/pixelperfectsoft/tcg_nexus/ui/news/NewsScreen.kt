package com.pixelperfectsoft.tcg_nexus.ui.news

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.gson.Gson
import com.pixelperfectsoft.tcg_nexus.model.classes.News
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NewsScreen() {
    val stream = LocalContext.current.assets.open("json/news.json")
    val size = stream.available()
    val buffer = ByteArray(size)
    stream.read(buffer)
    stream.close()
    val jsonStr = String(buffer)
    val json = Gson().fromJson(jsonStr, Array<News>::class.java)
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
        //Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        val state = rememberLazyListState()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            items(json) {
                NewsPanel(news = it, state = state)
            }
        }
    }
}

@Composable
fun NewsPanel(news: News, state: LazyListState) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
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

        LaunchedEffect(Unit) {
            val image = context.assets.open(news.image)
            bitmapState.value = BitmapFactory.decodeStream(image)
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (image, provider, title, date) = createRefs()
            if (bitmapState.value != null) {
                val bitmap = bitmapState.value!!.asImageBitmap()
                Image(
                    bitmap = bitmap,
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
            }
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
}