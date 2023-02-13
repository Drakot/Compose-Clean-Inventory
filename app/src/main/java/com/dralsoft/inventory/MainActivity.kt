package com.dralsoft.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dralsoft.inventory.core.navigation.NavRoutes
import com.dralsoft.inventory.detail.ui.InventoryScreen
import com.dralsoft.inventory.list.ui.ListInventoryScreen
import com.dralsoft.inventory.ui.theme.InventoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoutes.Inventories.route) {
        composable(route = NavRoutes.Inventories.route) {
            ListInventoryScreen(navController)
        }
        composable(
            route = NavRoutes.Inventory.route,
            arguments = NavRoutes.Inventory.arguments
        ) {
            InventoryScreen(navController, NavRoutes.Inventory.fromEntry(it))
        }
    }
}
