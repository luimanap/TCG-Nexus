package com.pixelperfectsoft.tcg_nexus.ui.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelperfectsoft.tcg_nexus.model.classes.User
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.UserDataViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.UserState
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyButton
import com.pixelperfectsoft.tcg_nexus.ui.MyPasswordField
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun Profile(
    navController: NavHostController,
    dataViewModel: UserDataViewModel = viewModel(),
) {
    dataViewModel.getUser()
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
        Log.d("Profile", "Username: $currentuser")
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        AvatarImage(
            viewModel = dataViewModel,
            currentuser = currentuser,
            navController = navController
        )
        UserInfo(currentuser)
        EditProfile()
    }
}

@Composable
fun EditProfile() {
    val context = LocalContext.current
    val pass = rememberSaveable { mutableStateOf("") }
    val newpass = rememberSaveable { mutableStateOf("") }
    Spacer(modifier = Modifier.fillMaxHeight(0.3f))
    MyPasswordField(
        data = pass.value,
        label = "Current password",
        onvaluechange = {
            pass.value = it
        },
        supporting_text = "Incorrect password",
        iserror = false
    )
    MyPasswordField(
        data = pass.value,
        label = "New password",
        onvaluechange = {
            newpass.value = it
        },
        supporting_text = "Incorrect password",
        iserror = false
    )

    Spacer(Modifier.size(8.dp))

    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    MyButton(
        text = "Change Password",
        onclick = {
            Toast.makeText(context, "Comming soon", Toast.LENGTH_SHORT).show()
        },
        containercolor = MaterialTheme.colorScheme.primary,
        bordercolor = MaterialTheme.colorScheme.primary,
        textcolor = Color.White
    )


}

@Composable
fun ProfileSelector(
    show: MutableState<Boolean>,
    currentuser: User,
    navcontroller: NavHostController,
) {
    Dialog(onDismissRequest = { show.value = false }) {
        val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
        val context = LocalContext.current
        val bitmapImages = mutableListOf<Bitmap?>()
        val avatarImages = mutableListOf<String>()

        for (i in LocalContext.current.assets.list("avatars")!!) {
            Log.d("avatars", "Loading avatar -> avatars/$i")
            if (i != null) {
                bitmapState.value =
                    BitmapFactory.decodeStream(context.assets.open("avatars/$i"))
                bitmapImages.add(bitmapState.value)
                avatarImages.add("avatars/$i")
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Select an avatar:",
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    columns = GridCells.Fixed(4),
                    content = {
                        items(avatarImages) { image ->
                            if (bitmapState.value != null) {
                                val bitmap =
                                    bitmapImages[avatarImages.indexOf(image)]!!.asImageBitmap()
                                Text(text = image)
                                Image(
                                    //painter = rememberAsyncImagePainter(model = image),
                                    bitmap = bitmap,
                                    contentDescription = "",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            Log.d("avatar", "New profile -> $image")
                                            changeAvatar(image, currentuser)
                                            show.value = false
                                            navcontroller.navigate(MyScreenRoutes.LOGIN)
                                        })
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun AvatarImage(
    viewModel: UserDataViewModel,
    currentuser: User,
    navController: NavHostController,
) {

    val show = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
    Log.d("avatar", currentuser.avatar_url)
    when (val result = viewModel.state.value) {
        is UserState.Loading -> {
            CircularProgressIndicator()
        }

        is UserState.Success -> {
            LaunchedEffect(Unit) {
                val avatar = context.assets.open(currentuser.avatar_url)
                bitmapState.value = BitmapFactory.decodeStream(avatar)
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .size(200.dp)
                    //.padding(top = 16.dp)
                    .clickable {
                        show.value = true
                    },
                contentAlignment = Alignment.Center
            ) {

                if (bitmapState.value != null) {
                    val bitmap = bitmapState.value!!.asImageBitmap()
                    Image(
                        bitmap = bitmap, contentDescription = "Avatar",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        is UserState.Empty -> {}
        is UserState.Failure -> {}
    }


    if (show.value) {
        ProfileSelector(//avatarImages = avatarImages,
            show = show, currentuser = currentuser, navcontroller = navController
        )
    }
}

fun changeAvatar(image: String, currentUser: User) {
    /*
     * Cambiando el avatar del usuario:
     * 1. Mostramos el usuario que se tiene que actualizar en Firebase por consola
     * 2. Obtenemos los documentos de la coleccion "users" que tengan el mismo "user_id"
     *    que el usuario actualmente logueado
     * 3. Por cada uno que encuentra obtenemos el documento y actualizamos el campo "avatar_id"
     *    por la ruta de la imagen que pasamos como parametro al metodo
     * 4. Mostramos los logs de actualizado correctamente o error si hubiese alguno
     */
    Log.d("avatar_update", "Updating ${currentUser.user_id}")
    FirebaseFirestore.getInstance()
        .collection("users")
        .whereEqualTo("user_id", currentUser.user_id)
        .get()
        .addOnSuccessListener {
            for (doc in it.documents) {
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(doc.id)
                    .update("avatar_url", image)
                    .addOnSuccessListener {
                        Log.d("avatar_update", "Avatar successfully updated!")
                    }.addOnFailureListener { e ->
                        Log.d("avatar_update", "Error updating avatar $e")
                    }
            }
        }
}


@Composable
fun UserInfo(user: User) {
    Spacer(modifier = Modifier.fillMaxHeight(0.025f))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = user.display_name,
            style = TextStyle(
                fontSize = 35.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = user.email,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
        )
    }
}