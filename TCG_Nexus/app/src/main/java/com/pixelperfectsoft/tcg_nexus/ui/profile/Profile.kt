package com.pixelperfectsoft.tcg_nexus.ui.profile

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.classes.User
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.UserDataViewModel
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelperfectsoft.tcg_nexus.ui.MyButton
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.StorageConfig
import com.pixelperfectsoft.tcg_nexus.ui.MyPasswordField
import com.pixelperfectsoft.tcg_nexus.ui.MyTextField
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun Profile(
    navController: NavHostController,
    dataViewModel: UserDataViewModel = viewModel(),

    ) {
    //Creamos un array con las rutas de los avatares a partir de los elementos existentes dentro de la carpeta avatars
    // Si la lista devuelve nulo utilizamos el operador ?: para asegurarnos de que se cree una lista mutable vacia
    val avatarImages =
        LocalContext.current.assets.list("avatars")?.mapNotNull { "avatars/$it" }?.toMutableList()
            ?: mutableListOf()
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
        Log.d("Profile", "Username: ${currentuser.displayName}")
        LogOutButton(navController)
        AvatarImage(avatarImages = avatarImages, currentuser = currentuser, navController = navController)
        UserInfo(currentuser)
        EditProfile(currentuser, navController = navController)
    }
}

@Composable
fun EditProfile(currentuser: User, navController: NavHostController) {
    val newdisplayname = rememberSaveable { mutableStateOf(currentuser.displayName) }
    val newemail = rememberSaveable { mutableStateOf(currentuser.email) }
    val pass = rememberSaveable { mutableStateOf("") }
    val newpass = rememberSaveable{ mutableStateOf("") }
    Spacer(modifier = Modifier.fillMaxHeight(0.1f))

    MyTextField(
        data = newdisplayname.value,
        label = "Usuario",
        onvaluechange = {
            newdisplayname.value = it
        }, //Siempre que escribamos algo el boolean error se va a poner en false
        supportingText = "",
        iserror = false
    )
    MyTextField(
        data = newemail.value,
        label = "Correo electrónico",
        onvaluechange = {
            newemail.value = it
        }, //Siempre que escribamos algo el boolean error se va a poner en false
        supportingText = "",
        iserror = false
    )
    MyPasswordField(
        data = pass.value,
        label = "Contraseña actual",
        onvaluechange = {
            pass.value = it
        }, //Siempre que escribamos algo el boolean error se va a poner en false
        supporting_text = "Contraseña incorrecta",
        iserror = false
    )
    MyPasswordField(
        data = pass.value,
        label = "Nueva contraseña",
        onvaluechange = {
            pass.value = it
        }, //Siempre que escribamos algo el boolean error se va a poner en false
        supporting_text = "Contraseña incorrecta",
        iserror = false
    )

    Spacer(Modifier.size(8.dp))

    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    MyButton(
        text = "Editar perfil",
        onclick = {

        },
        containercolor = MaterialTheme.colorScheme.primary,
        bordercolor = MaterialTheme.colorScheme.primary,
        textcolor = Color.White
    )


}

@Composable
fun ProfileSelector(
    show: MutableState<Boolean>,
    avatarImages: MutableList<String>,
    currentuser: User,
    navcontroller: NavHostController,
) {
    Dialog(onDismissRequest = { show.value = false }) {
        val context = LocalContext.current


        Surface(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Selecciona una imagen de perfil:",
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
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
                                        /*
                                        * Al hacer click en la imagen que deseamos:
                                        * 1. Mostramos por consola la ruta de la imagen para facilitar
                                        *    la depuración
                                        * 2. Cambiamos la imagen en Firebase
                                        * 3. Cerramos el dialogo
                                        * 4. Navegamos a la pantalla de login, que comprueba si
                                        *    estamos logueados, lo que recarga la página en su defecto
                                        */
                                        Log.d("avatar", "New profile -> $image")
                                        changeAvatar(image, currentuser)
                                        show.value = false
                                        navcontroller.navigate(MyScreenRoutes.LOGIN)
                                    })
                        }
                    })
            }
        }
    }
}

@Composable
fun AvatarImage(
    currentuser: User,
    navController: NavHostController,
    avatarImages: MutableList<String>
) {
    var show = rememberSaveable {
        mutableStateOf(false)
    }
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
            model = currentuser.avatarUrl, contentDescription = "Avatar",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    if (show.value) {
        ProfileSelector(avatarImages = avatarImages,
            show = show, currentuser = currentuser, navcontroller = navController)
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
    Log.d("avatar_update", "Updating ${currentUser.userId}")
    FirebaseFirestore.getInstance()
        .collection("users")
        .whereEqualTo("user_id", currentUser.userId)
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
            /*
             * Cerrando sesion:
             * 1. Navegamos a la pantalla de login
             * 2. Cerramos la sesion actual en Firebase
             */
            navController.navigate(MyScreenRoutes.LOGIN)
            FirebaseAuth.getInstance().signOut()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun UserInfo(user: User) {
    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
    Text(
        text = "User: "+ user.displayName,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}