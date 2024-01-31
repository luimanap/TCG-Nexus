package com.pixelperfectsoft.tcg_nexus.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.pixelperfectsoft.tcg_nexus.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.MyButton
import com.pixelperfectsoft.tcg_nexus.MyCanvasSeparator
import com.pixelperfectsoft.tcg_nexus.MyLogo
import com.pixelperfectsoft.tcg_nexus.MyPasswordField
import com.pixelperfectsoft.tcg_nexus.MyTextField
import com.pixelperfectsoft.tcg_nexus.cards.createGradientBrush
import com.pixelperfectsoft.tcg_nexus.navigation.MyAppRoute

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
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
            .fillMaxSize()
            .background(createGradientBrush(backcolors))
            .clip(RoundedCornerShape(40.dp))
            .zIndex(150f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*if(showLoginForm){
            LoginForm(navController, viewModel)
        }else{

        }*/
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()) {
            //navController.navigate(MyAppRoute.PROFILE)
            LoginForm(navController, viewModel)
        } else {
            navController.navigate(MyAppRoute.PROFILE)
        }

    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginScreenViewModel) {

    var userinput by rememberSaveable { mutableStateOf("") }
    var passinput by rememberSaveable { mutableStateOf("") }
    //Login header
    Spacer(Modifier.size(225.dp))
    Text(text = "INICIAR SESION", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
    Spacer(Modifier.size(25.dp))

    //Username Input
    MyTextField(data = userinput, label = "Correo Electrónico", onvaluechange = { userinput = it })
    Spacer(Modifier.size(16.dp))

    //Password Input
    MyPasswordField(data = passinput, label = "Contraseña", onvaluechange = { passinput = it })
    Spacer(Modifier.size(8.dp))

    //Forgotten password button
    ForgottenPasswordButton()
    Spacer(modifier = Modifier.size(100.dp))

    //Login button
    MyButton(
        text = "Iniciar Sesión",
        onclick = {
            viewModel.signIn(email = userinput, password = passinput, profile = {
                navController.navigate(MyAppRoute.PROFILE)
            }/*, error = {
                ErrorDialog("Usuario o contraseña incorrectos")
            }*/)
        },
        containercolor = Color(92, 115, 255),
        bordercolor = Color(92, 115, 255),
        textcolor = Color.White
    )
    Spacer(Modifier.size(8.dp))

    //Separator between buttons
    MyCanvasSeparator()

    Spacer(Modifier.size(8.dp))
    //Register Button
    MyButton(
        text = "Crear Cuenta",
        onclick = { navController.navigate("register") },
        containercolor = Color(41, 188, 117),
        bordercolor = Color(41, 188, 117),
        textcolor = Color.White
    )
}

@Composable
fun ErrorDialog(text: String) {
    var show by rememberSaveable {mutableStateOf(false)}
        AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ }, text = { Text(
            text = text
        )})
}

@Composable
fun ForgottenPasswordButton() {
    val context = LocalContext.current
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 210.dp, top = 20.dp),
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.None
                )
            ) {
                append("¿Olvidaste tu contraseña?")
                addStringAnnotation(
                    tag = "",
                    annotation = "",
                    start = 0,
                    end = length
                )
            }
        },
        onClick = { Toast.makeText(context, "Password Forgotten", Toast.LENGTH_SHORT).show() },
        style = TextStyle(
            fontSize = 15.sp
        )
    )
}
