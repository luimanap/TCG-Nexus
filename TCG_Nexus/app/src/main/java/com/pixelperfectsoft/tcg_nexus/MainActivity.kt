@file:OptIn(ExperimentalMaterial3Api::class)

package com.pixelperfectsoft.tcg_nexus

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pixelperfectsoft.tcg_nexus.ui.theme.TCGNexus_Theme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.pixelperfectsoft.tcg_nexus.model.viewmodel.CardViewModel
import com.pixelperfectsoft.tcg_nexus.ui.navigation.BottomBarNaviContainer
import com.pixelperfectsoft.tcg_nexus.ui.navigation.MyScreenRoutes
import com.pixelperfectsoft.tcg_nexus.ui.navigation.NaviActions


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val activity = LocalContext.current as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val navController = rememberNavController()
            val navigateAction = remember(navController) { NaviActions(navController) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val selectedDestination = navBackStackEntry?.destination?.route
                ?: MyScreenRoutes.LOGIN //Destino inicial de navegación
            /*
            val cardViewModel = CardViewModel(context)
            cardViewModel.load(context)
            */
            TCGNexus_Theme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomBarNaviContainer(
                        navController = navController,
                        selectedDestination = selectedDestination,
                        navigateTo = navigateAction::navigateTo
                    )
                }
            }
        }
    }
}