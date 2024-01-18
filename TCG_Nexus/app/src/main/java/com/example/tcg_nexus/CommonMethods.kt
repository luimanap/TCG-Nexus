package com.example.tcg_nexus

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dragon_background),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun MyLogo(height: Int) {
    Image(
        painterResource(id = R.drawable.logotcgnexus), "Logo",
        Modifier
            .fillMaxWidth()
            .height(height.dp)
    )
}

@Composable
fun MyButton(text: String, onclick: () -> Unit, containercolor: Color) {
    Button(
        onClick = onclick,
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            containerColor = containercolor,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun MyCanvasSeparator() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {

        val strokeWidth = 0.6.dp.toPx() // Line Width
        val startY = size.height / 2   //Half of Y axis (Vertical center)
        val centerX = size.width / 2    // Half of X axis (Horizontal center
        val halfLineWidth = size.width / 3
        val lineColor = Color.Black     //Line color

        //Draw left line
        drawLine(
            color = lineColor,
            start = Offset(centerX - halfLineWidth, startY),
            end = Offset(halfLineWidth + centerX / 4, startY),
            strokeWidth = strokeWidth
        )

        //Draw right line
        drawLine(
            color = lineColor,
            start = Offset(centerX + centerX / 10, startY),
            end = Offset(centerX + halfLineWidth, startY),
            strokeWidth = strokeWidth
        )

        // Draw circle in center
        drawCircle(
            color = lineColor,
            style = Stroke(width = strokeWidth),
            radius = strokeWidth * 5,
            center = Offset(centerX, startY)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(data: String, label: String, onvaluechange: (String) -> Unit) {
    TextField(
        value = data,
        onValueChange = onvaluechange,
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        shape = RoundedCornerShape(45.dp),
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPasswordField(data: String, label: String, onvaluechange: (String) -> Unit) {
    TextField(
        value = data,
        onValueChange = onvaluechange,
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        shape = RoundedCornerShape(45.dp),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun BottomBarNaviContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDestination: String,
    navigateTo: (MenuItems) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(navController = navController)
                }
                composable("register") {
                    RegisterScreen(navController = navController)
                }
                composable(MyAppRoute.HOME) {
                    HomeScreen(navController = navController)
                }
                composable(MyAppRoute.COLLECTION) {

                }
                composable(MyAppRoute.DECKS) {

                }
                composable(MyAppRoute.PLAY) {
                    PlayScreen(navController = navController)
                }
                composable(MyAppRoute.PROFILE) {

                }
            }
            BottomBarNavigation(selectedDestination = selectedDestination, navigateTo = navigateTo)
        }
    }
}

@Composable
fun BottomBarNavigation(selectedDestination: String, navigateTo: (MenuItems) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destinations ->
            NavigationBarItem(
                selected = selectedDestination == destinations.path,
                onClick = { navigateTo(destinations) },
                icon = {
                    Icon(
                        painter = painterResource(id = destinations.icon),
                        contentDescription = stringResource(id = destinations.textId),
                        modifier = Modifier.size(24.dp)
                    )
                })
        }
    }
}