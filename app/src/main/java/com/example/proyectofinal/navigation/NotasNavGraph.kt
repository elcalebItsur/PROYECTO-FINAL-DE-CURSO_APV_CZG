package com.example.proyectofinal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyectofinal.ui.screens.ListaNotasScreen
import com.example.proyectofinal.ui.screens.CrearNotaScreen
import com.example.proyectofinal.ui.screens.EditarNotaScreen
import com.example.proyectofinal.ui.screens.DetalleNotaScreen
import com.example.proyectofinal.ui.screens.RecordatoriosScreen
import com.example.proyectofinal.ui.screens.AjustesScreen

@Composable
fun NotasNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListaNotas.route
    ) {
        composable(Screen.ListaNotas.route) {
            ListaNotasScreen(
                onNotaClick = { notaId ->
                    navController.navigate(Screen.DetalleNota.createRoute(notaId))
                },
                onNuevaNotaClick = {
                    navController.navigate(Screen.CrearNota.route)
                }
            )
        }

        composable(Screen.CrearNota.route) {
            CrearNotaScreen(
                onNotaCreada = { notaId ->
                    navController.navigate(Screen.DetalleNota.createRoute(notaId)) {
                        popUpTo(Screen.ListaNotas.route)
                    }
                },
                onCancelar = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.EditarNota.route,
            arguments = listOf(
                navArgument("notaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val notaId = backStackEntry.arguments?.getString("notaId") ?: return@composable
            EditarNotaScreen(
                notaId = notaId,
                onNotaActualizada = {
                    navController.navigateUp()
                },
                onCancelar = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.DetalleNota.route,
            arguments = listOf(
                navArgument("notaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val notaId = backStackEntry.arguments?.getString("notaId") ?: return@composable
            DetalleNotaScreen(
                notaId = notaId,
                onEditarClick = { 
                    navController.navigate(Screen.EditarNota.createRoute(notaId))
                },
                onRecordatoriosClick = {
                    navController.navigate(Screen.Recordatorios.createRoute(notaId))
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.Recordatorios.route,
            arguments = listOf(
                navArgument("notaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val notaId = backStackEntry.arguments?.getString("notaId") ?: return@composable
            RecordatoriosScreen(
                notaId = notaId,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Ajustes.route) {
            AjustesScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}