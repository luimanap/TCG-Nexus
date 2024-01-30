package com.pixelperfectsoft.tcg_nexus.profile

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.cards.createGradientBrush

@Composable
fun Profile(navController: NavHostController) {
    val backcolors = listOf(
        Color.Transparent,
        Color(230, 230, 230),
        Color(225, 225, 225),
        Color(225, 225, 225),
        Color(225, 225, 225)
    )
    BackgroundImage()

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(modifier = Modifier
                .size(50.dp)
                .clickable {


                }) {
                Icon(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "logout",
                    tint = Color.Red,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
        //Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Box(
            Modifier
                .clip(CircleShape)
                .border(1.dp, SolidColor(Color.Black), CircleShape)
                .background(Color.Transparent)
                .size(160.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.personcirclesharp),
                contentDescription = "Profile picture",
                modifier = Modifier.fillMaxSize()
            )
        }
        UserInfo()

    }
}

@Composable
fun UserInfo() {
    Spacer(modifier = Modifier.fillMaxHeight(0.01f))
    Text(
        text = "Username", style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
    Spacer(modifier = Modifier.fillMaxHeight(0.2f))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.fillMaxWidth(0.1f))
        Card(modifier = Modifier.fillMaxSize(0.4f)) {
            Text(
                text = "Cards", style = TextStyle(
                    fontSize = 30.sp
                )
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.1f))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.4f)
        ) {
            Text(
                text = "Decks", style = TextStyle(
                    fontSize = 30.sp
                )
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth())
    }


}
