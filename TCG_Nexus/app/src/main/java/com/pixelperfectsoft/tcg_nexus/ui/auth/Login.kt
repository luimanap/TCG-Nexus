package com.pixelperfectsoft.tcg_nexus.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
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
import com.pixelperfectsoft.tcg_nexus.ui.OutlineButton
import com.pixelperfectsoft.tcg_nexus.ui.CanvasSeparator
import com.pixelperfectsoft.tcg_nexus.ui.PasswordField
import com.pixelperfectsoft.tcg_nexus.ui.Textfield
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.LoginViewModel
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelperfectsoft.tcg_nexus.ui.Logo
import com.pixelperfectsoft.tcg_nexus.ui.isValidEmail
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {
    val backcolors = listOf(
        Color.Transparent,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.background,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(navController: NavController, viewModel: LoginViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var error by rememberSaveable { mutableStateOf(false) }
    var userinput by rememberSaveable { mutableStateOf("") }
    var passinput by rememberSaveable { mutableStateOf("") }

    //Login header
    Spacer(Modifier.fillMaxHeight(0.05f))
    Logo(height = 225)
    Spacer(Modifier.fillMaxHeight(0.05f))
    Text(text = "LOG IN", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp))
    Spacer(Modifier.size(25.dp))

    //email input
    Textfield(
        data = userinput,
        label = "Email",
        onvaluechange = {
            userinput = it
            error = false
        },
        supportingText = "Incorrect or bad formatted email",
        iserror = error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
    )
    Spacer(Modifier.size(16.dp))

    //password input
    PasswordField(
        data = passinput,
        label = "Password",
        onvaluechange = {
            passinput = it; error = false
        }, //Siempre que escribamos algo el boolean error se va a poner en false
        supporting_text = "Incorrect password",
        iserror = error,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )

    //Forgotten password button
    ForgottenPasswordButton(sheetState, scope)
    Spacer(modifier = Modifier.fillMaxHeight(0.2f))

    //Login button
    OutlineButton(
        text = "Log In",
        onclick = {
            if (userinput != "" && passinput != "") { //Si correo y contraseña no estan vacios
                viewModel.signIn(email = userinput.trim(), password = passinput, profile = {
                    navController.navigate(MyScreenRoutes.PROFILE) //Si el inicio de sesion es correcto navegamos a la ventana del perfil
                }, onError = {
                    error =
                        true //Si el inicio de sesion es incorrecto ponemos el boolean error en true
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

    //Separator
    CanvasSeparator()

    Spacer(Modifier.size(8.dp))
    //Register button
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
    if (sheetState.isVisible) {
        ForgottenPasswordSheet(sheetState, scope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgottenPasswordSheet(sheetState: SheetState, scope: CoroutineScope) {
    val loginViewModel = LoginViewModel()
    val context = LocalContext.current
    val passwordinput = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    ModalBottomSheet(
        modifier = Modifier.navigationBarsPadding(),
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
        content = {
            Textfield(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                iserror = error.value,
                supportingText = "Enter a valid email",
                data = email.value,
                label = "Email",
                onvaluechange = { email.value = it; error.value = false })
            Spacer(modifier = Modifier.height(12.dp))
            OutlineButton(
                text = "Reset Password",
                onclick = {
                    if (email.value.isNullOrBlank() || !isValidEmail(email.value)) {
                        error.value = true
                    }else{
                        loginViewModel.resetpassword(email.value)
                    }
                },
                containercolor = MaterialTheme.colorScheme.primary,
                bordercolor = MaterialTheme.colorScheme.primary,
                textcolor = Color.White
            )
            Spacer(modifier = Modifier.height(50.dp))

        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgottenPasswordButton(
    sheetState: SheetState,
    scope: CoroutineScope,
) {
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
                append("¿Forgot your password?")
                addStringAnnotation(
                    tag = "",
                    annotation = "",
                    start = 0,
                    end = length
                )
            }
        },
        onClick = {
            scope.launch {
                sheetState.show()
            }
        },
        style = TextStyle(
            fontSize = 15.sp
        )
    )
}
