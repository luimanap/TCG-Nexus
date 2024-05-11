package com.pixelperfectsoft.tcg_nexus.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyButton
import com.pixelperfectsoft.tcg_nexus.ui.MyCanvasSeparator
import com.pixelperfectsoft.tcg_nexus.ui.MyPasswordField
import com.pixelperfectsoft.tcg_nexus.ui.MyTextField
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.LoginViewModel
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var termschecked by rememberSaveable {mutableStateOf(false)}
    var policychecked by rememberSaveable {mutableStateOf(false)}
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    val error by rememberSaveable { mutableStateOf(false) }
    var userinput by rememberSaveable { mutableStateOf("") }
    var emailinput by rememberSaveable { mutableStateOf("") }
    var passinput by rememberSaveable { mutableStateOf("") }
    var confpassinput by rememberSaveable { mutableStateOf("") }
    BackgroundImage()
    Column(
        Modifier
            .fillMaxSize()
            .background(createGradientBrush(backcolors))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(Modifier.size(35.dp))
        MyLogo(height = 100)
        Spacer(Modifier.size(20.dp))
        Text(text = "CREAR CUENTA", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp))


        //Name Input
        MyTextField(data = userinput, label = "Usuario", onvaluechange = { userinput = it },supportingText = "", iserror = error)


        //Email Input
        MyTextField(
            data = emailinput,
            label = "Correo Electrónico",
            onvaluechange = { emailinput = it },supportingText = "", iserror = error)

        //Password Input

        MyPasswordField(data = passinput, label = "Contraseña", onvaluechange = { passinput = it }, supporting_text = "", iserror = error)
        //Spacer(Modifier.size(8.dp))

        //Confirm Password Input
        MyPasswordField(
            data = confpassinput,
            label = "Confirmar Contraseña",
            onvaluechange = { confpassinput = it },
            supporting_text = "", iserror = error)

        //Checkboxes
        Box(){
            Column {
                termschecked = myTextCheckBox("Acepto los términos y condiciones")
                policychecked = myTextCheckBox("Acepto la política de privacidad")
                myTextCheckBox("Me gustaría recibir ofertas y promociones exclusivas")
            }
        }
        Spacer(modifier = Modifier.size(8.dp))

        //Register button
        MyButton(
            text = "Create Account",
            onclick = {
                if (passinput == confpassinput && termschecked && policychecked) {
                    viewModel.createUserAccount(userinput, emailinput, passinput) {
                        navController.navigate(MyScreenRoutes.PROFILE)
                    }
                }
            },
            containercolor = Color(92, 115, 255),
            bordercolor = Color(92, 115, 255),
            textcolor = Color.White
        )

        //Separator
        Spacer(Modifier.size(8.dp))
        MyCanvasSeparator()
        Spacer(Modifier.size(8.dp))

        //Login Button
        OutlinedButton(
            onClick = { navController.popBackStack() },
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
            Text(text = "Log In")
        }
    }
}

@Composable
fun myTextCheckBox(text: String): Boolean {
    var state by rememberSaveable { mutableStateOf(false) }
    Row(Modifier.padding(start = 30.dp, end = 30.dp)) {
        Checkbox(
            checked = state, onCheckedChange = { state = !state }, colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Black,
                checkedColor = Color.Blue,
                checkmarkColor = Color.White
            )
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(color = Color.Black),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    return state
}