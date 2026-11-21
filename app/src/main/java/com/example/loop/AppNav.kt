package com.example.loop

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "lista") {

        composable("lista") {
            ListaScreen(
                onNueva = { nav.navigate("nueva") },
                onDetalle = { id -> nav.navigate("detalle/$id") }
            )
        }

        composable("nueva") {
            NuevaTareaScreen(onGuardado = { nav.popBackStack() })
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            DetalleScreen(
                id = entry.arguments?.getInt("id") ?: -1,
                onVolver = { nav.popBackStack() }
            )
        }
    }
}