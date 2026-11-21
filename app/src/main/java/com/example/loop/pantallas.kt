package com.example.loop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// ---------- COMPONENTE REUTILIZABLE ----------
@Composable
fun TareaItem(tarea: Tarea, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(tarea.titulo, style = MaterialTheme.typography.titleMedium)
            Text(tarea.asignatura, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// ---------- PANTALLA: LISTA ----------
@Composable
fun ListaScreen(onNueva: () -> Unit, onDetalle: (Int) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNueva) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("Mis tareas", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(TareasRepo.tareas, key = { it.id }) { tarea ->
                    TareaItem(tarea) { onDetalle(tarea.id) }
                }
            }
        }
    }
}// ---------- COMPONENTE REUTILIZABLE: CAMPO DE TEXTO ----------
@Composable
fun CampoTexto(valor: String, onChange: (String) -> Unit, etiqueta: String, error: String?) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(etiqueta) },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        modifier = Modifier.fillMaxWidth()
    )
}

// ---------- PANTALLA: NUEVA TAREA ----------
@Composable
fun NuevaTareaScreen(onGuardado: () -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var errores by remember { mutableStateOf(emptyMap<String, String>()) }

    Column(
        Modifier.statusBarsPadding().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Nueva tarea", style = MaterialTheme.typography.headlineMedium)
        CampoTexto(titulo, { titulo = it }, "Título", errores["titulo"])
        CampoTexto(asignatura, { asignatura = it }, "Asignatura", errores["asignatura"])
        CampoTexto(descripcion, { descripcion = it }, "Descripción", errores["descripcion"])
        Button(onClick = {
            errores = validarTarea(titulo, asignatura, descripcion)
            if (errores.isEmpty()) {
                TareasRepo.agregar(titulo, asignatura, descripcion)
                onGuardado()
            }
        }) { Text("Guardar") }
    }
}// ---------- PANTALLA: DETALLE ----------
@Composable
fun DetalleScreen(id: Int, onVolver: () -> Unit) {
    val tarea = TareasRepo.buscar(id)

    Column(
        Modifier.statusBarsPadding().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Detalle de la tarea", style = MaterialTheme.typography.headlineMedium)
        if (tarea == null) {
            Text("Tarea no encontrada")
        } else {
            Text("ID: ${tarea.id}")
            Text("Título: ${tarea.titulo}")
            Text("Asignatura: ${tarea.asignatura}")
            Text("Descripción: ${tarea.descripcion.ifBlank { "Sin descripción" }}")
        }
        Button(onClick = onVolver) { Text("Volver") }
    }
}