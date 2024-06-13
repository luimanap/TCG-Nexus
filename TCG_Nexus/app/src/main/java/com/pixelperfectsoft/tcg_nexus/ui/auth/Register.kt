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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var termschecked by rememberSaveable { mutableStateOf(false) }
    var policychecked by rememberSaveable { mutableStateOf(false) }
    val backcolors = listOf(
        Color.Transparent,
        Color.White,
        Color.White,
        Color.White,
        Color.White,
    )
    var passerror by rememberSaveable { mutableStateOf(false) }
    var confpasserror by rememberSaveable { mutableStateOf(false) }
    var termserror = rememberSaveable { mutableStateOf(false) }
    var policyerror = rememberSaveable { mutableStateOf(false) }
    var emailerror = rememberSaveable { mutableStateOf(false) }
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
        Text(
            text = "CREATE ACCOUNT",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
        )


        //Name Input
        MyTextField(
            data = userinput,
            label = "User",
            onvaluechange = { userinput = it },
            supportingText = "",
            iserror = passerror
        )


        //Email Input
        MyTextField(
            data = emailinput,
            label = "Email",
            onvaluechange = { emailinput = it }, supportingText = "Badly formatted email", iserror = emailerror.value
        )

        //Password Input

        MyPasswordField(
            data = passinput,
            label = "Password",
            onvaluechange = { passinput = it; passerror = false },
            supporting_text = "Password must be at least 6 characters",
            iserror = passerror,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        )
        //Spacer(Modifier.size(8.dp))
        PasswordStrengthBar(password = passinput)
        //Confirm Password Input
        MyPasswordField(
            data = confpassinput,
            label = "Confirm Pasword",
            onvaluechange = { confpassinput = it ; confpasserror = false },
            supporting_text = "Passwords mismatch",
            iserror = confpasserror,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        )

        //Checkboxes
        Box{
            Column {
                termschecked = myTextCheckBox(
                    text = "I accept the terms and conditions",
                    error = termserror,
                    errortext = "Please accept the terms and conditions"
                )
                policychecked = myTextCheckBox(
                    "I accept the Privacy Policy",
                    error = policyerror,
                    errortext = "Please accept the Privacy Policy"
                )
                myTextCheckBox(
                    "I would like to receive exclusive offers and promotions",
                    error = rememberSaveable{mutableStateOf(false)},
                    errortext = ""
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))

        //Register button
        MyButton(
            text = "Create Account",
            onclick = {
                if(!isValidEmail(emailinput)){
                    emailerror.value = true
                }
                if (passinput.length < 6) {
                    passerror = true
                }
                if (passinput != confpassinput) {
                    confpasserror = true
                }
                if (!termschecked) {
                    termserror.value = true
                }
                if (!policychecked) {
                    policyerror.value = true
                }

                if (!emailerror.value && !passerror && !confpasserror && !termserror.value && !policyerror.value) {
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
fun myTextCheckBox(text: String, error: MutableState<Boolean>, errortext: String): Boolean {
    var state by rememberSaveable { mutableStateOf(false) }
    Row(Modifier.padding(start = 30.dp, end = 30.dp)) {
        Checkbox(
            checked = state, onCheckedChange = { state = !state; error.value = false }, colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Black,
                checkedColor = Color.Blue,
                checkmarkColor = Color.White
            )
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = text,
                style = TextStyle(color = Color.Black),
            )
            if (error.value) {
                Text(text = errortext, style = TextStyle(color = Color.Red, fontSize = 12.sp))
            }
        }

    }
    return state
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return email.matches(emailRegex)
}