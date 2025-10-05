package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotasApp() {
    var searchExpanded by remember { mutableStateOf(false) }
    
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
                onClick = { /* TODO: Navegar a crear nota */ }
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
            // TODO: Implementar NavigationHost aquí
            Text("Contenido Principal")
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