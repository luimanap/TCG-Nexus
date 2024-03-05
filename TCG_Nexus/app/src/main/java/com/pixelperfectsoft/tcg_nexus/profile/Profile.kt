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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.model.StorageConfig
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush
import kotlinx.coroutines.tasks.await

@Composable
fun Profile(
    navController: NavHostController,
    dataViewModel: UserDataViewModel = viewModel(),

    ) {
    val currentuser = dataViewModel.user.value
    val backcolors = listOf(
        Color.Transparent,
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
        AvatarImage(currentuser = currentuser, navController = navController)
        UserInfo(currentuser)
        EditProfile(currentuser)
    }
}

@Composable
fun EditProfile(currentuser: User) {
    var show by rememberSaveable {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.fillMaxHeight(0.2f))
    MyButton(
        text = "Edit profile",
        onclick = { show = true },
        containercolor = MaterialTheme.colorScheme.primary,
        bordercolor = MaterialTheme.colorScheme.primary,
        textcolor = Color.White
    )

}

@Composable
fun AvatarImage(navController: NavController, currentuser: User, avatarImagesViewModel: StorageConfig = viewModel()) {
    val show = remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .size(200.dp)
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
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {
            val avatarImages = avatarImagesViewModel.images.value
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    //horizontalArrangement = Arrangement.Center,
                    columns = GridCells.Fixed(4),
                    content = {
                        items(avatarImages) { image ->
                            AsyncImage(
                                model = image,
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Log.d("avatar", "New profile -> $image")
                                        changeAvatar(image, currentuser)
                                        show.value = false
                                        navController.navigate(MyScreenRoutes.LOGIN)
                                    })
                        }
                    })
            }
        }
    }
}

fun changeAvatar(image: String, currentUser: User) {
    Log.d("avatar_update", "Updating ${currentUser.user_id}")
    FirebaseFirestore.getInstance().collection("users").whereEqualTo("user_id", currentUser.user_id).get()
        .addOnSuccessListener {
            for (doc in it.documents) {
                FirebaseFirestore.getInstance().collection("users").document(doc.id)
                    .update("avatar_url", image).addOnSuccessListener {
                        Log.d("avatar_update", "Avatar successfully updated!")
                    }.addOnFailureListener { e ->
                        Log.d("avatar_update", "Error updating avatar $e")
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
    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    Text(
        //text = "Username",
        text = user.display_name,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}
