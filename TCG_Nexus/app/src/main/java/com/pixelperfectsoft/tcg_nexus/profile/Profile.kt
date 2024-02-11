package com.pixelperfectsoft.tcg_nexus.profile

import android.util.Log
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.User
import com.pixelperfectsoft.tcg_nexus.model.UserDataViewModel
import com.pixelperfectsoft.tcg_nexus.navigation.MyScreenRoutes
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pixelperfectsoft.tcg_nexus.cards.CardItem
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun Profile(
    navController: NavHostController,
    dataViewModel: UserDataViewModel = viewModel()
) {
    val currentuser = dataViewModel.data.value
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    BackgroundImage()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .background(brush = createGradientBrush(backcolors)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogOutButton(navController)
        AvatarImage(currentuser)
        UserInfo(currentuser)
    }
}

@Composable
fun AvatarImage(currentuser: User) {
    var show = remember{ mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .size(160.dp)
            .clickable {
                show.value = true
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = currentuser.avatar_url, contentDescription = "Avatar",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    if (show.value){
        Dialog(onDismissRequest = { show.value = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                LazyVerticalGrid(horizontalArrangement = Arrangement.Center, columns = GridCells.Fixed(4), content = {
                    item{

                    }

                } )

            }
        }
    }
}

@Composable
fun LogOutButton(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp), horizontalArrangement = Arrangement.End
    ) {
        IconButton(modifier = Modifier
            .size(25.dp)
            .clip(CircleShape)
            .background(Color.White), onClick = {
            navController.navigate(MyScreenRoutes.LOGIN)
            FirebaseAuth
                .getInstance()
                .signOut()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                tint = Color.Red,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun UserInfo(user: User) {
    Spacer(modifier = Modifier.fillMaxHeight(0.01f))
    Text(
        //text = "Username",
        text = user.display_name,
        style = TextStyle(
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
            Text(
                text = user.cards.toString(), style = TextStyle(
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
            Text(
                text = user.decks.toString(), style = TextStyle(
                    fontSize = 30.sp
                )
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth())
    }


}
