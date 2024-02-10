package com.pixelperfectsoft.tcg_nexus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

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
fun GameBackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.woodtable),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )
    }
}

@Composable
fun MyLogo(height: Int) {
    Image(
        painterResource(id = R.drawable.logotcgnexus),
        "Logo",
        Modifier
            .fillMaxWidth()
            .height(height.dp)
    )
}

@Composable
fun MyButton(
    text: String,
    onclick: () -> Unit,
    containercolor: Color,
    bordercolor: Color,
    textcolor: Color,
) {
    OutlinedButton(
        onClick = onclick,
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            containerColor = containercolor,
            contentColor = textcolor
        ),
        border = BorderStroke(1.dp, bordercolor),
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
        val strokeWidth = 0.6.dp.toPx() //Anchura de la linea convertida a pixeles
        val startY = size.height / 2   //Obtenemos la mitad del eje Y en relacion a la pantalla
        val centerX = size.width / 2    //Obtenemos la mitad del eje X en relacion a la pantalla
        val halfLineWidth = size.width / 3 //Dividimos el eje X en 3 partes
        val lineColor = Color.Black   //Color de la linea

        //Dibujamos la linea de la izquierda
        drawLine(
            color = lineColor,
            start = Offset(centerX - halfLineWidth, startY), //Calculamos la posicion inicial de dibujado de la linea
            end = Offset(halfLineWidth + centerX / 4, startY), //Calculamos la posicion final de dibujado de la linea
            strokeWidth = strokeWidth
        )

        //Dibujamos la linea de la derecha
        drawLine(
            color = lineColor,
            start = Offset(centerX + centerX / 10, startY),
            end = Offset(centerX + halfLineWidth, startY),
            strokeWidth = strokeWidth
        )

        // Dibujamos el circulo de entra las dos lineas
        drawCircle(
            color = lineColor,
            style = Stroke(width = strokeWidth), //Indicamos que la anchura de la linea es el valor de la variable strokeWidth
            radius = strokeWidth * 5, //Calculamos el radio del circulo
            center = Offset(centerX, startY) //Calculamos el centro del circulo
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    iserror: Boolean,
    supporting_text: String,
    data: String,
    label: String,
    onvaluechange: (String) -> Unit,
) {
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
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            focusedSupportingTextColor = Color.Red,
            unfocusedSupportingTextColor = Color.Red
        ),
        supportingText = {
            if (iserror) {
                Text(text = supporting_text)
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPasswordField(
    supporting_text: String,
    data: String,
    label: String,
    onvaluechange: (String) -> Unit,
    iserror: Boolean,
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    val icon = if (!passwordVisibility) { //Variable para indicar que icono se muestra para cada estado de passwordVisibility.
        painterResource(id = R.drawable.passwordeye)
    } else {
        painterResource(id = R.drawable.passwordeyeopen)
    }
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
        visualTransformation = if (passwordVisibility) { //Si el boolean passwordVisibility esta en true, los caracteres van a poder ser legibles, pero si no, se va a aplicar una transformacion a los mismos
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), //Ponemos el teclado en modo contraseña, sin ajustes como el autocompletado
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            focusedSupportingTextColor = Color.Red,
            unfocusedSupportingTextColor = Color.Red
        ),
        trailingIcon = { //Boton para mostrar u ocultar los caracteres de la contraseña
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = icon,
                    contentDescription = "show_password",
                    modifier = Modifier.size(25.dp)
                )
            }
        },
        supportingText = { //Mensaje de error debajo del input. Si el boolean is error esta en true, se va a mostrar
            if (iserror) {
                Text(text = supporting_text)
            }
        }
    )
}

