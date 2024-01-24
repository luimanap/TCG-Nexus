package com.pixelperfectsoft.tcg_nexus.collection


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.Card
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.R
import androidx.compose.ui.window.Dialog as Dialog

@Composable
fun Collection(navController: NavController) {
    var cards = listOf(
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus),
        Card(name = "Black Lotus", description = "{T},Sacrifice Black Lotus: Add three mana of any one color. ", type = "Mono Artifact", subtype = "", rarity = "Rare", price = 3500f, image = R.drawable.blacklotus)
    )
    val backcolors = listOf(
        Color.Transparent,
        Color(230, 230, 230),
        Color(225, 225, 225),
        Color(225, 225, 225),
        Color(225, 225, 225)
    )
    val totalcards by rememberSaveable { mutableIntStateOf(cards.size) }
    var estimatedCost by rememberSaveable { mutableFloatStateOf(0f) }
    for (i in cards){
        estimatedCost += i.get_price()
    }
    val estimatedcostString by rememberSaveable { mutableStateOf("$estimatedCost €") }
    BackgroundImage()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors))
    ) {

        Spacer(modifier = Modifier.fillMaxHeight(0.05f))

        //Collection info
        Row(modifier = Modifier.fillMaxHeight(0.15f)) {
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            InfoCard(
                text = "Cartas en posesión",
                number = totalcards.toString(),
                containercolor = Color(92, 115, 255),
                contentcolor = Color.White,
                contenttype = "number"
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            InfoCard(
                text = "Valor estimado",
                number = estimatedcostString,
                containercolor = Color.White,
                contentcolor = Color.Black,
                contenttype = "number"
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        //Search Bar
        SearchButton()



        /*LazyColumn{
            items(cards){item ->
                ListItem(headlineText = {  })

            }
        }*/


        //Card list
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val show = rememberSaveable { mutableStateOf(false) }
            for (i in cards) {
                CardItem(cardname = i.get_name(),type = i.get_type(),description = i.get_description(), image = i.get_image(), show = show)
                if (show.value) {
                    CardDialog(show, i)
                }
            }

        }
    }
}

@Composable
fun SearchButton() {
    var show by rememberSaveable { mutableStateOf(false) }
    MyButton(
        text = "Search...",
        onclick = { show = true },
        containercolor = Color.White,
        bordercolor = Color.Black,
        textcolor = Color.Black
    )
    SearchDialog()
}

@Composable
fun SearchDialog() {

}

@Composable
fun CardDialog(show: MutableState<Boolean>, card: Card) {
    Dialog(onDismissRequest = { show.value = false }) {
        Row (Modifier.background(Color.White)){
            Column {
                Text(text = card.get_name())
                Text(text = card.get_type())
                card.get_subtype()?.let { Text(text = it) } //If card subtype is not null
                Text(text = card.get_description())
                Text(text = card.get_rarity())
                Text(text = card.get_price().toString()+"€")
            }
            Image(painter = painterResource(id = card.get_image()), contentDescription = card.get_name(), modifier = Modifier.fillMaxWidth(0.35f))
        }

    }

}

@Composable
fun CardItem(
    cardname: String,
    type: String,
    description: String,
    show: MutableState<Boolean>,
    image: Int
) {
    OutlinedButton(onClick = {
        show.value = true
    },
        border = BorderStroke(0.dp,Color.Transparent),
        modifier = Modifier.clip(shape = RectangleShape),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color.Transparent
        )
    ) {
        Row {
            Image(
                painter = painterResource(id = image),
                contentDescription = cardname,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .padding(16.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cardname,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = type,
                    modifier = Modifier.padding(start = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = description,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth(0.95f))
    }
}

@Composable
fun InfoCard(
    text: String,
    number: String,
    containercolor: Color,
    contentcolor: Color,
    contenttype: String,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = containercolor,
            contentColor = contentcolor
        )

    ) {
        Column {
            if (contenttype == "number") {
                Text(
                    text = text, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
                Text(
                    text = number, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp)
                        .padding(start = 10.dp), fontSize = 30.sp
                )
            } else if (contenttype == "text") {
                Text(
                    text = text, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = number, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp), fontSize = 30.sp
                )
            }
        }
    }
}

fun createGradientBrush(
    colors: List<Color>,
    isVertical: Boolean = true,
): Brush {
    val endOffset = if (isVertical) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset,
        tileMode = TileMode.Clamp
    )
}
