package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.navigation.NotasNavGraph
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasApp() {
    var searchExpanded by remember { mutableStateOf(false) }
    
    val navController = rememberNavController()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Notas y Tareas") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Abrir drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                },
                actions = {
                    IconButton(onClick = { searchExpanded = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("crear_nota") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Nota")
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NotasNavGraph(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotasAppPreview() {
    ProyectoFinalTheme {
        NotasApp()
    }
}