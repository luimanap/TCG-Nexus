package com.pixelperfectsoft.tcg_nexus.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
import com.pixelperfectsoft.tcg_nexus.navigation.MyAppRoute

@Composable
fun LoginScreen(navController: NavController,viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    //val showLoginForm by rememberSaveable { mutableStateOf(true) }

    BackgroundImage()
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color(150, 150, 150, 125))
            .clip(RoundedCornerShape(40.dp))
            .zIndex(150f)
    ) {
        /*if(showLoginForm){
            LoginForm(navController, viewModel)
        }else{

        }*/
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()){
            LoginForm(navController, viewModel)
        }
        else{
            navController.navigate(MyAppRoute.PROFILE)
        }

    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginScreenViewModel) {
    var userinput by rememberSaveable { mutableStateOf("") }
    var passinput by rememberSaveable { mutableStateOf("") }
    //Logo Image
    Spacer(Modifier.size(12.dp))
    MyLogo(height = 250)
    Spacer(Modifier.size(12.dp))

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
    Spacer(Modifier.size(70.dp))

    //Login button
    MyButton(
        text = "Iniciar Sesión",
        onclick = {
            viewModel.signIn(email = userinput, password = passinput){
                navController.navigate(MyAppRoute.PROFILE)
            }
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
