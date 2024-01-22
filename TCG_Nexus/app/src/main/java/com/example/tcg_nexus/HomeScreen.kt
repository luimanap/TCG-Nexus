package com.example.tcg_nexus

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {


    //BackgroundImage()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(210, 210, 210))
            .verticalScroll(rememberScrollState())
    ) {


    }
}

@Composable
fun NewsPanel(image: Image, title: String, link: String){
    Row {
        Box{

        }
        Column {
            Text(text = title)
        }
    }

}

