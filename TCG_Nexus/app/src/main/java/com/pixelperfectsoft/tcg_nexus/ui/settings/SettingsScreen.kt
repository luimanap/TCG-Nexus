package com.pixelperfectsoft.tcg_nexus.ui.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelperfectsoft.tcg_nexus.R
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CollectionViewModel
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.UserDataViewModel
import com.pixelperfectsoft.tcg_nexus.ui.BackgroundImage
import com.pixelperfectsoft.tcg_nexus.ui.MyLogo
import com.pixelperfectsoft.tcg_nexus.ui.MyPasswordField
import com.pixelperfectsoft.tcg_nexus.ui.cards.FilterModalSheet
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.theme.TCGNexus_Theme
import com.pixelperfectsoft.tcg_nexus.ui.theme.createGradientBrush
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val aboutdialog = remember { mutableStateOf(false) }
    val themedialog = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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

            if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()) {
                SettingsOption(
                    option = "My Account",
                    icon = R.drawable.personcirclesharp,
                    onClick = { scope.launch { sheetState.show() } })
            }
            SettingsOption(
                option = "Theme",
                icon = R.drawable.theme,
                onClick = {
                    themedialog.value = true
                })
            SettingsOption(option = "About", icon = R.drawable.info, onClick = {
                aboutdialog.value = true
            })
            if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrBlank()) {
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
        if (themedialog.value) {
            ThemeDialog(themedialog)
        }
        if (sheetState.isVisible) {
            MyAccountDialog(
                sheetState = sheetState,
                scope = scope,
                navController = navController,
                userviewModel = UserDataViewModel(),
                collectionViewModel =  CollectionViewModel()
            )
        }
    }
}

@Composable
fun ThemeDialog(themedialog: MutableState<Boolean>) {
    val colors = listOf(
        Color(92, 115, 255),
        Color(255, 92, 92),
        Color(92, 255, 92),
        Color(254, 255, 92)
    )
    val context = LocalContext.current
    var selectedColor by remember { mutableStateOf(colors[0]) }
    val darkmodeckecked = rememberSaveable { mutableStateOf(false) }


    Dialog(onDismissRequest = { themedialog.value = false }) {
        Surface(
            modifier = Modifier
                //.fillMaxHeight(0.5f)
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.8f),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "Theme",
                    fontWeight = FontWeight.ExtraBold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Dark Mode",
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.7f))
                    Switch(checked = darkmodeckecked.value, onCheckedChange = {
                        darkmodeckecked.value = !darkmodeckecked.value
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Select Color",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(70.dp)
                                .background(color, shape = CircleShape)
                                .clickable {
                                    selectedColor = color
                                    themedialog.value = false
                                }
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAccountDialog(
    sheetState: SheetState,
    scope: CoroutineScope,
    navController: NavHostController,
    userviewModel: UserDataViewModel,
    collectionViewModel: CollectionViewModel
) {
    val currentuser = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    val passwordinput = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }
    val pass = remember { mutableStateOf("") }
    ModalBottomSheet(
        modifier = Modifier.navigationBarsPadding(),
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    if (!passwordinput.value) {
                        passwordinput.value = true
                    } else {
                        val email = currentuser?.email
                        val credential = email?.let { EmailAuthProvider.getCredential(it, pass.value) }
                        if (credential != null) {
                            scope.launch {
                                userviewModel.deleteUser()
                                collectionViewModel.deleteCollection()
                            }
                            currentuser.reauthenticate(credential).addOnSuccessListener {
                                currentuser.delete().addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        navController.navigate(MyScreenRoutes.LOGIN)
                                        FirebaseAuth.getInstance().signOut()
                                        Toast.makeText(context,"Account Deleted Successfully",Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context,it.exception?.message,Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }.addOnFailureListener{
                                Toast.makeText(context,"Incorrect Password",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }) {
                    Text(text = "Delete my account")
                }
                if (passwordinput.value) {
                    Spacer(modifier = Modifier.height(10.dp))
                    MyPasswordField(
                        data = pass.value,
                        label = "Password",
                        onvaluechange = { pass.value = it; error.value = false }, //Siempre que escribamos algo el boolean error se va a poner en false
                        supporting_text = "Incorrect password",
                        iserror = error.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp))
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        })
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
            val color = when (option) {
                "Log Out" -> Color.Red
                "Language" -> Color(135, 206, 235)
                "My Account" -> Color.DarkGray
                "Theme" -> Color(255, 195, 85)
                "About" -> Color(173, 216, 230)
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