package com.pixelperfectsoft.tcg_nexus.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyButton
import com.pixelperfectsoft.tcg_nexus.ui.MyCanvasSeparator
import com.pixelperfectsoft.tcg_nexus.ui.MyPasswordField
import com.pixelperfectsoft.tcg_nexus.ui.MyTextField
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.LoginViewModel
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {
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
            .fillMaxSize()
            .background(createGradientBrush(backcolors))
            .clip(RoundedCornerShape(40.dp))
            .zIndex(150f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()) {
            LoginForm(navController, viewModel)
        } else {
            navController.navigate(MyScreenRoutes.COLLECTION)
        }

    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginViewModel) {
    var error by rememberSaveable { mutableStateOf(false) }
    var userinput by rememberSaveable { mutableStateOf("") }
    var passinput by rememberSaveable { mutableStateOf("") }

    //Login header
    Spacer(Modifier.fillMaxHeight(0.05f))
    MyLogo(height = 225)
    Spacer(Modifier.fillMaxHeight(0.05f))
    Text(text = "INICIAR SESION", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp))
    Spacer(Modifier.size(25.dp))

    //Input de correo electronico
    MyTextField(
        data = userinput,
        label = "Correo Electrónico",
        onvaluechange = { userinput = it; error = false }, //Siempre que escribamos algo el boolean error se va a poner en false
        supportingText = "Correo electronico incorrecto o mal formateado",
        iserror = error
    )
    Spacer(Modifier.size(16.dp))

    //Input de contraseña
    MyPasswordField(
        data = passinput,
        label = "Contraseña",
        onvaluechange = { passinput = it; error = false }, //Siempre que escribamos algo el boolean error se va a poner en false
        supporting_text = "Contraseña incorrecta",
        iserror = error)
    //Spacer(Modifier.size(4.dp))

    //Boton de contraseña olvidada
    ForgottenPasswordButton()
    Spacer(modifier = Modifier.fillMaxHeight(0.2f))

    //Boton de login
    MyButton(
        text = "Log In",
        onclick = {
            if (userinput != "" && passinput != "") { //Si correo y contraseña no estan vacios
                viewModel.signIn(email = userinput.trim(), password = passinput, profile = {
                    navController.navigate(MyScreenRoutes.PROFILE) //Si el inicio de sesion es correcto navegamos a la ventana del perfil
                }, onError = {
                    error = true //Si el inicio de sesion es incorrecto ponemos el boolean error en true
                })
            } else {
                error = true //Si correo y contraseña estan vacios ponemos el boolean error en true
            }

        },
        containercolor = MaterialTheme.colorScheme.primary,
        bordercolor = MaterialTheme.colorScheme.primary,
        textcolor = Color.White
    )


    Spacer(Modifier.size(8.dp))

    //Separador "-----o-----"
    MyCanvasSeparator()

    Spacer(Modifier.size(8.dp))
    //Boton de crear cuenta
    OutlinedButton(
        onClick = { navController.navigate("register") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .border(
                5.dp,
                MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        )
    ) {
        Text(text = "Create Account")
    }
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
