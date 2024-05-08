package com.pixelperfectsoft.tcg_nexus.ui.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.profile.changeAvatar
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun SettingsScreen(navController: NavHostController) {
    val aboutdialog = remember { mutableStateOf(false) }

    val colors = listOf(
        Color.Transparent,
        Color(255, 255, 255, 100),
        Color(255, 255, 255, 150),
        Color(255, 255, 255, 200),
        Color(255, 255, 255, 200),
        Color(255, 255, 255, 250),
        Color(255, 255, 255, 250),
        Color.White
    )
    BackgroundImage()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(createGradientBrush(colors = colors))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.07f))
        Column(
            modifier = Modifier.fillMaxSize()

        ) {
            SettingsOption(option = "My Account", onClick = {})
            SettingsOption(option = "About", onClick = {
                aboutdialog.value = true
                Log.d("aboutdialog", aboutdialog.value.toString())
            })
            Log.d("aboutdialog", aboutdialog.value.toString())
        }
        if (aboutdialog.value) {
            AboutDialog(aboutdialog)
        }
    }
}

@Composable
fun AboutDialog(aboutdialog: MutableState<Boolean>) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Dialog(onDismissRequest = { aboutdialog.value = false }) {
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight(0.86f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                    MyLogo(height = 165)

                        Text(
                            text = "TCG Nexus beta2024.5.15",
                            modifier = Modifier
                                .padding(top = 16.dp),
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                    Text(
                        text = "Developed by Luis Vaquero Gil from PixelPerfectSoftware",
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(0.5f))
                    Text(
                        text = "TCG Nexus and PixelPerfectSoftware are not affiliated with Wizards " +
                                "of the Coast LLC.",
                        modifier = Modifier.padding(top = 16.dp),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Wizards of the Coast, Magic: The Gathering, and their logos are " +
                                "trademarks of Wizards of the Coast LLC in the United States and " +
                                "other countries. © 1993-2024 Wizards. All Rights Reserved. MAGIC: " +
                                "THE GATHERING ® is a trademark of Wizards of the Coast. "+
                                "For more information about Wizards of the Coast or any of Wizards' " +
                                "trademarks or other intellectual property, please visit their website " +
                                "at https://company.wizards.com/.",
                        modifier = Modifier.padding(top = 16.dp),
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center
                    )

                }
                Spacer(modifier = Modifier.fillMaxHeight(0.3f))
                Button(onClick = {
                    Toast.makeText(context, "Opening Play Store", Toast.LENGTH_SHORT).show()
                    uriHandler.openUri("https://play.google.com/store/apps/")
                }) {
                    Text(text = "Rate this app!")
                }
            }

        }
    }
}

@Composable
fun SettingsOption(option: String, onClick: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(250, 250, 250),
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ), modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(8.dp),
        onClick = onClick

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option,
                //style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}