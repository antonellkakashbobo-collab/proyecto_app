package com.example.loop

import androidx.compose.runtime.mutableStateListOf

data class Tarea(
    val id: Int,
    val titulo: String,
    val asignatura: String,
    val descripcion: String
)

object TareasRepo {
    val tareas = mutableStateListOf(
        Tarea(1, "Informe de laboratorio", "Física", "Entregar en PDF"),
        Tarea(2, "Ensayo sobre la independencia", "Historia", "Mínimo 2 páginas"),
        Tarea(3, "Ejercicios de derivadas", "Cálculo", "Capítulo 4")
    )
    private var siguienteId = 4

    fun agregar(titulo: String, asignatura: String, descripcion: String) {
        tareas.add(Tarea(siguienteId++, titulo.trim(), asignatura.trim(), descripcion.trim()))
    }

    fun buscar(id: Int) = tareas.find { it.id == id }
}

fun validarTarea(titulo: String, asignatura: String, descripcion: String): Map<String, String> {
    val errores = mutableMapOf<String, String>()
    if (titulo.isBlank()) errores["titulo"] = "El título es obligatorio"
    else if (titulo.trim().length < 3) errores["titulo"] = "El título debe tener al menos 3 caracteres"
    if (asignatura.isBlank()) errores["asignatura"] = "La asignatura es obligatoria"
    if (descripcion.length > 200) errores["descripcion"] = "Máximo 200 caracteres"
    return errores
}