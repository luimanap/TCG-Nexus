package com.pixelperfectsoft.tcg_nexus.ui.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush

@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
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
            SettingsOption(
                option = "Language",
                icon = R.drawable.language,
                onClick = { Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show() })

            if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()){
                SettingsOption(
                    option = "My Account",
                    icon = R.drawable.personcirclesharp,
                    onClick = { Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show() })
            }
            SettingsOption(
                option = "Theme",
                icon = R.drawable.theme,
                onClick = { Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show() })
            SettingsOption(option = "About", icon = R.drawable.info, onClick = {
                aboutdialog.value = true
                Log.d("aboutdialog", aboutdialog.value.toString())
            })
            if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()) {
                SettingsOption(option = "Log Out", icon = R.drawable.logout, onClick = {
                    navController.navigate(MyScreenRoutes.LOGIN)
                    FirebaseAuth.getInstance().signOut()
                })
            }
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
                    MyLogo(height = 165)

                    Text(
                        text = "TCG Nexus beta2024.5.15",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Developed by Luis Vaquero Gil from DiceCraftersStudio",
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(0.5f))
                    Text(
                        text = "TCG Nexus and DiceCraftersStudio are not affiliated with Wizards " +
                                "of the Coast LLC.",
                        modifier = Modifier.padding(top = 16.dp),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Wizards of the Coast, Magic: The Gathering, and their logos are " +
                                "trademarks of Wizards of the Coast LLC in the United States and " +
                                "other countries. © 1993-2024 Wizards. All Rights Reserved. MAGIC: " +
                                "THE GATHERING ® is a trademark of Wizards of the Coast. " +
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
fun SettingsOption(option: String, icon: Int, onClick: () -> Unit) {
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
            val color = when(option) {
                "Log Out" -> Color.Red
                "Language" -> Color(135,206,235)
                "My Account" -> Color.DarkGray
                "Theme" -> Color(255, 195, 85)
                "About" -> Color(173,216,230)
                else -> Color.Gray
            }
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            Text(
                text = option,
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