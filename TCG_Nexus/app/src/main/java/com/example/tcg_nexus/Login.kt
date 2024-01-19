package com.example.tcg_nexus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var userinput by rememberSaveable { mutableStateOf("") }
    var passinput by rememberSaveable { mutableStateOf("") }
    BackgroundImage()
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color(150, 150, 150, 125))
            .clip(RoundedCornerShape(40.dp)).zIndex(150f)
    ) {

        //Logo Image
        Spacer(Modifier.size(50.dp))
        MyLogo(height = 250)
        Spacer(Modifier.size(25.dp))

        //Username Input
        MyTextField(data = userinput, label = "Username", onvaluechange = { userinput = it })
        Spacer(Modifier.size(16.dp))

        //Password Input
        MyPasswordField(data = passinput, label = "Password", onvaluechange = { passinput = it })
        Spacer(Modifier.size(8.dp))

        //Forgotten password button
        TextButton(
            onClick = { /*TODO*/ }, enabled = true, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color.Blue
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Text(
                text = "¿Olvidaste tu contraseña?", style = TextStyle(
                    fontSize = 15.sp
                )
            )
        }
        Spacer(Modifier.size(88.dp))

        //Login button
        MyButton(
            text = "Iniciar Sesión",
            onclick = { navController.navigate("home") },
            containercolor = Color(92, 115, 255)
        )
        Spacer(Modifier.size(8.dp))

        //Separator between buttons
        MyCanvasSeparator()

        Spacer(Modifier.size(8.dp))
        //Register Button
        MyButton(
            text = "Crear Cuenta",
            onclick = { navController.navigate("register") },
            containercolor = Color(41, 188, 117)
        )
    }
}