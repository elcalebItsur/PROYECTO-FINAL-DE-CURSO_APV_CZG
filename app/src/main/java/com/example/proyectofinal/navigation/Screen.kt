package com.example.proyectofinal.navigation

sealed class Screen(val route: String) {
    object ListaNotas : Screen("lista_notas")
    object CrearNota : Screen("crear_nota")
    object EditarNota : Screen("editar_nota/{notaId}") {
        fun createRoute(notaId: String) = "editar_nota/$notaId"
    }
    object DetalleNota : Screen("detalle_nota/{notaId}") {
        fun createRoute(notaId: String) = "detalle_nota/$notaId"
    }
    object Recordatorios : Screen("recordatorios/{notaId}") {
        fun createRoute(notaId: String) = "recordatorios/$notaId"
    }
    object Ajustes : Screen("ajustes")
}