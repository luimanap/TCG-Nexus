
package com.pixelperfectsoft.tcg_nexus

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pixelperfectsoft.tcg_nexus.ui.navigation.BottomBarNaviContainer
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.navigation.NaviActions
import com.pixelperfectsoft.tcg_nexus.ui.theme.PrimaryBlue
import com.pixelperfectsoft.tcg_nexus.ui.theme.PrimaryGreen
import com.pixelperfectsoft.tcg_nexus.ui.theme.PrimaryRed
import com.pixelperfectsoft.tcg_nexus.ui.theme.PrimaryYellow
import com.pixelperfectsoft.tcg_nexus.ui.theme.TCGNexus_Theme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme = if(isSystemInDarkTheme()){
                remember {mutableStateOf(true)}
            }else{
                remember {mutableStateOf(false)}
            }

            val selectedColor = remember { mutableStateOf(PrimaryBlue) }
            val colors = listOf(PrimaryBlue,PrimaryRed,PrimaryGreen,PrimaryYellow)

            val activity = LocalContext.current as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val navController = rememberNavController()
            val navigateAction = remember(navController) { NaviActions(navController) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val selectedDestination = navBackStackEntry?.destination?.route ?: MyScreenRoutes.LOGIN
            TCGNexus_Theme(primaryColor = selectedColor.value, darkTheme = isDarkTheme.value) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomBarNaviContainer(
                        primaryColor = selectedColor,
                        darktheme = isDarkTheme,
                        navController = navController,
                        selectedDestination = selectedDestination,
                        navigateTo = navigateAction::navigateTo
                    )
                }
            }
        }
    }
}